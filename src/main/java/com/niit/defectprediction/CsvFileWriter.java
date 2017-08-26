package com.niit.defectprediction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
	
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "Actual,RegWt,ReqSize,ReqQuality,Prediction";

	public static void writeCsvFile(String fileName, List<TestDataResult> predResultList) {
		
		FileWriter fileWriter = null;
				
		try {
			fileWriter = new FileWriter(fileName);

			//Write the CSV file header
			fileWriter.append(FILE_HEADER.toString());
			
			//Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			//Write a new student object list to the CSV file
			for (TestDataResult trainedData : predResultList) {
				fileWriter.append(String.valueOf(trainedData.getActual()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(trainedData.getRegwt()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(trainedData.getReqsize()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(trainedData.getReqquality()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(trainedData.getPredicted()));
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
