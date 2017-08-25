package com.niit.defectprediction;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Query implements Serializable{
	private final String projectId;
	private final List<TestData> testDatas;
   

	public Query(String projectId,List<TestData> testDatas){
    	this.projectId = projectId;
		this.testDatas = testDatas;
    }
	
	public String getProjectId() {
		return projectId;
	}

	
	public List<TestData> getTestDatas() {
			return testDatas;
	}


    	
}
