package com.jdc.progress.service.listeners;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.progress.mode.entity.EscCustomer;
import com.jdc.progress.mode.entity.EscCustomerPk;
import com.jdc.progress.mode.entity.EscInvoice;
import com.jdc.progress.mode.entity.EscUploadHistory;
import com.jdc.progress.mode.entity.EscUploadHistory.UploadState;
import com.jdc.progress.mode.repo.EscCustomerRepo;
import com.jdc.progress.mode.repo.EscInvoiceRepo;
import com.jdc.progress.mode.repo.EscUploadHistoryRepo;
import com.jdc.progress.service.ProgressMessageService;
import com.jdc.progress.service.StateMessageService;

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
	
	@Autowired
	private EscCustomerRepo customerRepo;
	
	
	@RabbitListener(queues = "#{createQueue.name}")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void handle(String message) {
		
		var historyId = UUID.fromString(message);
		var history = historyRepo.getReferenceById(historyId);
		
		try {
			
			// Listed files in directory
			var files = Files.list(Path.of(storage, message)).filter(Files::isRegularFile).toList();
			
			for(var i =0; i < files.size(); i ++) {
				try(var reader = Files.newBufferedReader(files.get(i))) {
					String line = null;
					while(null != (line = reader.readLine())) {
						
						// Create Customer
						var customer = getCustomer(history.getSystem(), line);
						
						// Create Invoice
						var invoice = getInvoice(history, customer, line);
						
						invoiceRepo.save(invoice);
					}
				}
				
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


	private EscInvoice getInvoice(EscUploadHistory history, EscCustomer customer, String line) {
		// TODO Auto-generated method stub
		return null;
	}


	private EscCustomer getCustomer(String system, String line) {

		var array = line.split("\t");
		var pk = new EscCustomerPk();
		pk.setService(system);
		pk.setCustomerId(array[2]);
		
		var entity = customerRepo.findById(pk).orElse(new EscCustomer());
		entity.setLedgerNo(array[1]);
		entity.setMeterNo(array[3]);
		entity.setName(array[4]);
		entity.setAddress(array[5]);
		
		return customerRepo.save(entity);
	}


}
