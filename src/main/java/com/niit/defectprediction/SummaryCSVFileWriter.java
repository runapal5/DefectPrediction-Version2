package com.niit.defectprediction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author runa
 * 
 */
public class SummaryCSVFileWriter {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "ReqId,ReqName,Total No. Of TestCases,Test Case Runs,Predicted Failures";

	public static void writeCsvFile(String fileName, List<RequestDetails> predResultList) {
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			for (RequestDetails moduleData : predResultList) {
				fileWriter.append(String.valueOf(moduleData.getRequirementId().intValue()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(moduleData.getReqName()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(moduleData.getTotalTC().intValue()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(moduleData.getTotalTCRun().intValue()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(moduleData.getTotalFail()));

				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			
			
			System.out.println("CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}
