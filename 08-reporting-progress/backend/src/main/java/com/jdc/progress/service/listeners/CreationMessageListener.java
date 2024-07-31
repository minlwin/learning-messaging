package com.jdc.progress.service.listeners;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.mode.entity.EscInvoice;
import com.jdc.progress.mode.entity.EscInvoicePk;
import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;
import com.jdc.progress.mode.repo.EscInvoiceRepo;
import com.jdc.progress.mode.repo.EscUploadHistoryRepo;
import com.jdc.progress.service.ProgressMessageService;
import com.jdc.progress.service.StateMessageService;
import com.jdc.progress.utils.BillLastDateUtils;

@Service
public class CreationMessageListener {

	private static DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Value("${app.esc.storage-path}")
	private String storage;

	@Autowired
	private ProgressMessageService progressMessageService;
	
	@Autowired
	private StateMessageService stateMessageService;
	
	@Autowired
	private EscUploadHistoryRepo historyRepo;
	
	@Autowired
	private EscInvoiceRepo invoiceRepo;
	
	@RabbitListener(queues = "#{createQueue.name}")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void handle(String message) {
		
		var historyId = UUID.fromString(message);
		var history = historyRepo.getReferenceById(historyId);
		
		try {
			
			// Listed files in directory
			var files = Files.list(Path.of(storage, message)).filter(Files::isRegularFile).toList();
			
			for(var i =0; i < files.size(); i ++) {
				var invoicesForFile = new ArrayList<EscInvoice>();
				try(var reader = Files.newBufferedReader(files.get(i))) {
					String line = null;
					while(null != (line = reader.readLine())) {
						var invoice = getInvoice(history.getSystem(), line);
						invoice.setHistory(history);
						invoice.setMtbDueDate(BillLastDateUtils.calculate(invoice.getId().getLastDate()));
						invoicesForFile.add(invoice);
					}
				}
				
				invoiceRepo.saveAllAndFlush(invoicesForFile);
				
				// Send Progress Message
				progressMessageService.send(historyId, UploadState.Create, i + 1, files.size());
			}
			
			// Update Upload History
			history.setState(UploadState.Create);
			history.setCreatedAt(LocalDateTime.now());

			// Next Process
			stateMessageService.send(historyId, UploadState.Success);
			
		} catch (Exception e) {
			// Send Progress Message
			progressMessageService.sendError(historyId, UploadState.Create);
			// Next Process
			stateMessageService.send(historyId, UploadState.Error);
			throw new RuntimeException(e);
		}
	}


	private EscInvoice getInvoice(String service, String line) {
		var array = line.split("\t");
		
		var pk = new EscInvoicePk();
		pk.setService(service);
		pk.setCustomerId(array[2]);
		pk.setLastDate(LocalDate.parse(array[7], DF));
		
		var invoice = new EscInvoice();
		invoice.setId(pk);
		invoice.setSeqNumber(Integer.parseInt(array[0]));
		invoice.setLedgerNo(array[1]);
		invoice.setMeterNo(array[3]);
		invoice.setCustomerName(array[4]);
		invoice.setAddress(array[5]);
		invoice.setBillCode(array[6]);
		invoice.setUsageUnit(Integer.parseInt(array[8]));
		invoice.setUsageFees(Integer.parseInt(array[9]));
		invoice.setServiceCharges(Integer.parseInt(array[10]));
		invoice.setHorsePower(Integer.parseInt(array[11]));
		invoice.setDiscount(Integer.parseInt(array[12]));
		invoice.setLastBalance(Integer.parseInt(array[13]));
		invoice.setTotal(Integer.parseInt(array[14]));
		invoice.setRemainAmount(Integer.parseInt(array[15]));
		invoice.setConnectionFees(Integer.parseInt(array[16]));
		invoice.setAllTotal(Integer.parseInt(array[17]));
		
		return invoice;
	}

}
