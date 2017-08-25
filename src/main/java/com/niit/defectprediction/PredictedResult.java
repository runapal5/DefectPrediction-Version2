package com.niit.defectprediction;

import java.io.Serializable;
import java.util.List;

public class PredictedResult implements Serializable{
	 private final List<TestDataResult> results;

	
	 public PredictedResult(List<TestDataResult> results) {
	        this.results = results;
	    }

	   
	  public List<TestDataResult> getResults() {
			return results;
		}

	 

	    @Override
	    public String toString() {
	        return "PredictedResult{" +
	                "results=" + results +
	                '}';
	    }
}
