package com.niit.defectprediction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class RequirementMapReader {

	    //Delimiter used in CSV file
		private static final String COMMA_DELIMITER = ",";
		
		private static final int REQ_ID_IDX = 0;
		private static final int REQ_NAME_IDX = 1;
		
		public static HashMap<Double,String> readCsvFile(String fileName) {

			BufferedReader fileReader = null;
			HashMap<Double,String> reqMap = new HashMap<Double,String>();
	        try {
	        	
	        	//Create a new list of student to be filled by CSV file data 
	        	
	        	
	            String line = "";
	            
	            //Create the file reader
	            fileReader = new BufferedReader(new FileReader(fileName));
	            
	            //Read the CSV file header to skip it
	            fileReader.readLine();
	            
	            //Read the file line by line starting from the second line
	            while ((line = fileReader.readLine()) != null) {
	                //Get all tokens available in line
	                String[] tokens = line.split(COMMA_DELIMITER);
	                if (tokens.length > 0) {
	                	//Create a new student object and fill his  data
	                	reqMap.put(Double.parseDouble(tokens[REQ_ID_IDX]), tokens[REQ_NAME_IDX]);
					}
	            }
	            
	            
	        } 
	        catch (Exception e) {
	        	System.out.println("Error in CsvFileReader !!!");
	            e.printStackTrace();
	        } finally {
	            try {
	                fileReader.close();
	            } catch (IOException e) {
	            	System.out.println("Error while closing fileReader !!!");
	                e.printStackTrace();
	            }
	        }
	        return reqMap;

		}
		
	
}
