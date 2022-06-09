package com.texoit.api.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.texoit.api.exceptions.BadRequestException;
import com.texoit.api.exceptions.ServiceException;

public abstract class CsvHelper {
	
	private static final String TYPE = "text/csv";
	private static final String DELIMITTER = ";";
	private static final int INDEX_HEADER = 0;
	private static final Integer MAX_ROWS_CSV = 500;
	
	private static Boolean validCSVFormat(String fileType) {
		 if (!TYPE.equals(fileType)) {
			 return Boolean.FALSE;
		 }
	    return Boolean.TRUE;
	}
	
	private static Boolean validHeaderCSV(MultipartFile file, String[] header) throws IOException {
		BufferedReader csvBuffer = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
		if(csvBuffer != null) {
			var lineHeader = csvBuffer.readLine();
			String[] values = lineHeader.split(DELIMITTER);
			if(Arrays.equals(values, header)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
		
	}
	
	public static List<List<String>> readCSV(MultipartFile file, String[] header) throws IOException {
		List<List<String>> records = new ArrayList<List<String>>();
		
		if(!validCSVFormat(file.getContentType())) {
			throw new BadRequestException("Invalid File Format.Allow only CSV files.");
		}
		
		if(!validHeaderCSV(file, header)) {
			throw new BadRequestException("The CSV header is incorret.");
		}
		
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
		
			String line;
		    while ((line = buffer.readLine()) != null) {
		        String[] values = line.split(DELIMITTER);
		        records.add(Arrays.asList(values));
		    }
		    records.remove(INDEX_HEADER);
		    
		    if(records.size() > MAX_ROWS_CSV) {
		    	throw new BadRequestException("The maximum rows number is " + MAX_ROWS_CSV + ".");
		    }
		    return records;
		}
		catch(BadRequestException ex) {
			throw new BadRequestException(ex.getMessage());
		}
		catch(ServiceException ex) {
			throw new ServiceException(ex.getMessage());
		}
	}
}
