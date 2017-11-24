package com.niit.defectprediction;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Comparator;





public class CSVTrainedDataReader {

	private static final String COMMA_DELIMITER = ",";
	
	
	public static HashMap<String,Integer> readCsvFile(String fileName) {

		BufferedReader fileReader = null;
		HashMap<String,Integer> reqTestMapDefect = new HashMap<String,Integer>();
        try {
        	
            String line = "";
            
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));
            
            //Read the CSV file header to skip it
            fileReader.readLine();
            
            
            HashMap<String,ArrayList<Integer>> reqTestMapDefectList = new HashMap<String,ArrayList<Integer>>();
            
           
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                	//Create a new student object and fill his  data
					String reqId =  tokens[4]; //Requirement ID
					String testId = tokens[5]; //Test ID
					String runCycle = tokens[6]; //Run Cycle
					String defectCount = tokens[7]; //Defect Count
					
					  if(reqTestMapDefect.containsKey(reqId+"_"+testId))
					  {
						  ArrayList<Integer> defectCountDtlList = reqTestMapDefectList.get(reqId+"_"+testId);
						  defectCountDtlList.add(Integer.parseInt(defectCount.trim()));
						  reqTestMapDefectList.put(reqId+"_"+testId, defectCountDtlList);
						  
					  }else{
						  ArrayList<Integer> defectCountDtlList = new ArrayList<Integer>();
						  defectCountDtlList.add(Integer.parseInt(defectCount.trim()));						  
						  reqTestMapDefectList.put(reqId+"_"+testId, defectCountDtlList);
					  }
					  
					  
					
					//testCaseSummarys.add(testCaseSummary);
				}
            }
            
            
            Iterator<Map.Entry<String,ArrayList<Integer>>> reqTestMapDefectListIterator = reqTestMapDefectList.entrySet().iterator();
            while(reqTestMapDefectListIterator.hasNext()){
	            Map.Entry<String,ArrayList<Integer>> entry = reqTestMapDefectListIterator.next();
	           /* System.out.printf("Key : %s and Value: %s %n", entry.getKey(), 
	                                                           entry.getValue().size());*/
	            //iterator.remove(); // right way to remove entries from Map,  avoids ConcurrentModificationException
	            ArrayList<Integer> defectCountDtlList = entry.getValue();
	            Iterator<Integer> defectCountDtlListItr = defectCountDtlList.iterator();
	            Integer defectCountSumPerReqTest = 0;
	            while(defectCountDtlListItr.hasNext()){
	            	Integer defectCount = defectCountDtlListItr.next();
	            	defectCountSumPerReqTest = defectCountSumPerReqTest + defectCount;
	            }
	            reqTestMapDefect.put(entry.getKey() , defectCountSumPerReqTest/defectCountDtlList.size());
	            
	           
		  }
            
           return reqTestMapDefect;
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
		return reqTestMapDefect;

	}

	
	
}
