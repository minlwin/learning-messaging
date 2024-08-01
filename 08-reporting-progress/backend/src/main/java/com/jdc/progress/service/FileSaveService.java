package com.jdc.progress.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jdc.progress.model.entity.EscUploadHistory.UploadState;
import com.jdc.progress.model.repo.EscUploadHistoryRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileSaveService {
	
	@Value("${app.esc.temp-file-size}")
	private int maxFileSize;
	
	@Value("${app.esc.storage-path}")
	private String storage;
	
	@Autowired
	private ProgressMessageService progressMessageService;
	
	@Autowired
	private StateMessageService stateMessageService;
	
	@Autowired
	private EscUploadHistoryRepo historyRepo;

	@Async
	@Transactional
	public void save(UUID id, MultipartFile file) {
		
		log.info("Async Start -> {}", id.toString());
			
		// Read Excel file
		var inputData = readExcelFile(id, file);
		
		if(null == inputData) {
			return;
		}
		
		Integer totalFiles = inputData.size();
		Integer current = 0;

		try {
			
			log.info("Save Files Start -> {}", id.toString());
			
			// Create Directory
			var directory = Path.of(storage, id.toString());
			Files.createDirectories(directory);

			for(var fileName : inputData.keySet()) {
				
				var splitedFile = directory.resolve(fileName);
				Files.write(splitedFile, inputData.get(fileName));
				
				// Send Message for Progress
				current ++;
				progressMessageService.send(id, UploadState.Save, current, totalFiles);
			}
			
			
			// Update Upload History
			historyRepo.findById(id).ifPresent(history -> {
				history.setState(UploadState.Save);
				history.setSavedAt(LocalDateTime.now());
			});
			
			// Send Message to proceed validation
			stateMessageService.send(id, UploadState.Validate);

		} catch (Exception e) {
			// Publish error message to progress queue
			progressMessageService.sendError(id, UploadState.Save, e.getMessage());

			// Send Message to proceed validation
			stateMessageService.send(id, UploadState.Error);
		}
	}

	private Map<String, List<String>> readExcelFile(UUID id, MultipartFile input) {

		log.info("Read Excel Start -> {}", id.toString());

		try(var book = new XSSFWorkbook(input.getInputStream())) {
			
			var map = new LinkedHashMap<String, List<String>>();
			
			var iterator = book.sheetIterator();
			
			while(iterator.hasNext()) {
				var sheet = iterator.next();

				String sliptFileName = null;
				List<String> lines = null;
				Integer splitedFiles = getSplitedFile(sheet.getLastRowNum());
				
				for(int rowNum = 1, split = 0, file = 0; rowNum <= sheet.getLastRowNum(); rowNum ++, split ++) {
					
					if(file == 0 || split == maxFileSize) {
						split = 0;
						sliptFileName = "split-%04d.txt".formatted(++ file);
						lines = new ArrayList<>();
						map.put(sliptFileName, lines);
						progressMessageService.send(id, UploadState.Read, split, splitedFiles);
					}
					
					lines.add(readRow(sheet.getRow(rowNum))); 
				}

				// Update Upload History
				historyRepo.findById(id).ifPresent(history -> {
					history.setState(UploadState.Read);
					history.setReadAt(LocalDateTime.now());
					history.setRecords(map.values().stream().mapToInt(a -> a.size()).sum());
				});

			}
			
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			// Publish error message to progress queue
			progressMessageService.sendError(id, UploadState.Read, e.getMessage());
			// Send Message to proceed validation
			stateMessageService.send(id, UploadState.Error);
			return null;
		}
	}
		
	private Integer getSplitedFile(int lastRowNum) {
		
		int split = lastRowNum / maxFileSize;
		var remain = lastRowNum % maxFileSize;
		
		split = (remain == 0) ? split : split + 1;
		
		return split;
	}

	private String readRow(Row row) {
		
		var list = new ArrayList<String>();
		var iterator = row.cellIterator();
		
		while(iterator.hasNext()) {
			var cell = iterator.next();
			var value = switch(cell.getCellType()) {
			case STRING -> cell.getStringCellValue();
			case NUMERIC -> {
				Double cellValue = cell.getNumericCellValue();
				yield String.valueOf(cellValue.intValue());
			}
			default -> "";
			};
			
			list.add(value);
		}
		
		return list.stream().collect(Collectors.joining("\t"));
	}	

}
