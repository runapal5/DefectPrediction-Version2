package com.niit.defectprediction;

import java.io.Serializable;
import java.util.List;

public class PredictedResult implements Serializable{
	 private final List<RequestDetails> results;

	
	 public PredictedResult(List<RequestDetails> results) {
	        this.results = results;
	    }

	   
	  public List<RequestDetails> getResults() {
			return results;
		}

	 

	    @Override
	    public String toString() {
	        return "PredictedResult{" +
	                "results=" + results +
	                '}';
	    }
}
