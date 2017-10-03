package com.niit.defectprediction;

import java.io.Serializable;
import java.util.List;

public class PredictedResult implements Serializable{
	 private final ProgramData programData;

	
	 public PredictedResult(ProgramData programData) {
	        this.programData = programData;
	    }

	   
	public ProgramData getProgramData() {
		return programData;
	}





		@Override
	    public String toString() {
	        return "PredictedResult{" +
	                "programData=" + programData +
	                '}';
	    }
}
