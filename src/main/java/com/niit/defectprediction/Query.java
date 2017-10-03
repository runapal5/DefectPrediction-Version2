package com.niit.defectprediction;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Query implements Serializable{
	private final String projectId;
	//private final String testDataPath;
   
	public Query(String projectId){
    	this.projectId = projectId;
		
    }
	
    /*
	public Query(String projectId,String testDataPath){
    	this.projectId = projectId;
		this.testDataPath = testDataPath;
    }
    */
	
	public String getProjectId() {
		return projectId;
	}

	/*
	public String getTestDataPath() {
		return testDataPath;
	}
	*/
    	
}
