package com.niit.defectprediction;

import java.util.List;

public class ProgramData {

	 private Integer totalNumberOfRequirement;
	 private Integer totalNumberOfTestCases;
	 private Integer totalNumberOfDefectPredicted;
	 private Integer totalNumberOfTestCaseFailed;
	 private Integer totalNumberOfTestCycleRun;
	 private List<RequestDetails> results;
	 
	 
	 public ProgramData(Integer totalNumberOfRequirement, Integer totalNumberOfTestCases,Integer totalNumberOfDefectPredicted ,Integer totalNumberOfTestCaseFailed ,Integer totalNumberOfTestCycleRun ,List<RequestDetails> results)
	 {
		 this.totalNumberOfRequirement = totalNumberOfRequirement;
		 this.totalNumberOfTestCases = totalNumberOfTestCases;
		 this.totalNumberOfDefectPredicted = totalNumberOfDefectPredicted;
		 this.totalNumberOfTestCaseFailed = totalNumberOfTestCaseFailed ;
		 this.totalNumberOfTestCycleRun = totalNumberOfTestCycleRun;
		 this.results = results;
	 }


	public Integer getTotalNumberOfTestCaseFailed() {
		return totalNumberOfTestCaseFailed;
	}


	public void setTotalNumberOfTestCaseFailed(Integer totalNumberOfTestCaseFailed) {
		this.totalNumberOfTestCaseFailed = totalNumberOfTestCaseFailed;
	}


	public Integer getTotalNumberOfTestCycleRun() {
		return totalNumberOfTestCycleRun;
	}


	public void setTotalNumberOfTestCycleRun(Integer totalNumberOfTestCycleRun) {
		this.totalNumberOfTestCycleRun = totalNumberOfTestCycleRun;
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
        		"totalNumberOfTestCaseFailed=" + totalNumberOfTestCaseFailed +
        		"totalNumberOfTestCycleRun=" + totalNumberOfTestCycleRun +
                "results=" + results +
                '}';
    }
	
}
