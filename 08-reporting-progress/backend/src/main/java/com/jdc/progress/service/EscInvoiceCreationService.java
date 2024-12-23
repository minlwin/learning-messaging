package com.jdc.progress.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.progress.model.entity.EscInvoice;
import com.jdc.progress.model.entity.EscInvoice.Status;
import com.jdc.progress.model.entity.EscInvoicePk;
import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscInvoiceRepo;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;
import com.jdc.progress.utils.BillLastDateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EscInvoiceCreationService {

	
	private static DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	@Value("${app.esc.storage-path}")
	private String storage;

	private final ProgressMessageService progressMessageService;
	private final EscInvoiceRepo invoiceRepo;
	private final EscUploadHistoryRepo historyRepo;

	@Transactional
	public boolean create(UUID historyId) {
		
		var history = historyRepo.getReferenceById(historyId);
		
		try {
			
			// Listed files in directory
			var files = Files.list(Path.of(storage, historyId.toString())).filter(Files::isRegularFile).toList();
			progressMessageService.send(historyId, UploadState.Create, 0, files.size());
			
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
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			// Send Progress Message
			progressMessageService.sendError(historyId, UploadState.Create, e.getMessage());			
			return false;
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
		invoice.setSeqNumber(parseInt(array[0]));
		invoice.setLedgerNo(array[1]);
		invoice.setMeterNo(array[3]);
		invoice.setCustomerName(array[4]);
		invoice.setAddress(array[5]);
		invoice.setBillCode(array[6]);
		invoice.setUsageUnit(parseInt(array[8]));
		invoice.setUsageFees(parseInt(array[9]));
		invoice.setServiceCharges(parseInt(array[10]));
		invoice.setHorsePower(parseInt(array[11]));
		invoice.setDiscount(parseInt(array[12]));
		invoice.setLastBalance(parseInt(array[13]));
		invoice.setTotal(parseInt(array[14]));
		invoice.setRemainAmount(parseInt(array[15]));
		invoice.setConnectionFees(parseInt(array[16]));
		invoice.setAllTotal(parseInt(array[17]));
		
		String [] codes = pk.getCustomerId().split("-");
		
		invoice.setStatus(Status.New);

		if(codes.length == 2) {
			invoice.setTownship(codes[0]);
		}
		
		return invoice;
	}
	
	private static Integer parseInt(String str) {
		if(StringUtils.hasLength(str)) {
			return Integer.parseInt(str);
		}
		
		return null;
	}	
}
