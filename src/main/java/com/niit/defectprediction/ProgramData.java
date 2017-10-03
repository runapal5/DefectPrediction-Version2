package com.niit.defectprediction;

import java.util.List;

public class ProgramData {

	 private Integer totalNumberOfRequirement;
	 private Integer totalNumberOfTestCases;
	 private Integer totalNumberOfDefectPredicted;
	 private List<RequestDetails> results;
	 
	 
	 public ProgramData(Integer totalNumberOfRequirement, Integer totalNumberOfTestCases,Integer totalNumberOfDefectPredicted ,List<RequestDetails> results)
	 {
		 this.totalNumberOfRequirement = totalNumberOfRequirement;
		 this.totalNumberOfTestCases = totalNumberOfTestCases;
		 this.totalNumberOfDefectPredicted = totalNumberOfDefectPredicted;
		 this.results = results;
	 }


	public Integer getTotalNumberOfRequirement() {
		return totalNumberOfRequirement;
	}


	public Integer getTotalNumberOfTestCases() {
		return totalNumberOfTestCases;
	}


	public Integer getTotalNumberOfDefectPredicted() {
		return totalNumberOfDefectPredicted;
	}


	public List<RequestDetails> getResults() {
		return results;
	}

	 
	@Override
    public String toString() {
        return "ProgramData{" +
        		"totalNumberOfRequirement=" + totalNumberOfRequirement +
        		"totalNumberOfTestCases=" + totalNumberOfTestCases +
        		"totalNumberOfDefectPredicted=" + totalNumberOfDefectPredicted +
                "results=" + results +
                '}';
    }
	
}
