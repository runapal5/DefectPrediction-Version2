package com.niit.defectprediction;



public class RequestDetails implements Comparable<RequestDetails>{
	
	private Double requirementId;
	private Integer totalTC;
	private Integer totalTCRun;
	private Integer totalFail;
	private Integer totalDefectPredicted;
	private String reqName;
	
	public RequestDetails(Double requirementId,Integer totalTC, Integer totalTCRun, Integer totalFail, Integer totalDefectPredicted , String reqName){
		
		this.requirementId = requirementId;
		this.totalTC = totalTC ;
		this.totalTCRun = totalTCRun;
		this.totalFail = totalFail;
		this.totalDefectPredicted = totalDefectPredicted;
		this.reqName = reqName;
	}

	
	
	public String getReqName() {
		return reqName;
	}



	public void setReqName(String reqName) {
		this.reqName = reqName;
	}



	public Double getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Double requirementId) {
		this.requirementId = requirementId;
	}

	public Integer getTotalTC() {
		return totalTC;
	}

	public void setTotalTC(Integer totalTC) {
		this.totalTC = totalTC;
	}

	public Integer getTotalTCRun() {
		return totalTCRun;
	}

	public void setTotalTCRun(Integer totalTCRun) {
		this.totalTCRun = totalTCRun;
	}

	

	public Integer getTotalFail() {
		return totalFail;
	}

	public void setTotalFail(Integer totalFail) {
		this.totalFail = totalFail;
	}
	
	
	public Integer getTotalDefectPredicted() {
		return totalDefectPredicted;
	}

	public void setTotalDefectPredicted(Integer totalDefectPredicted) {
		this.totalDefectPredicted = totalDefectPredicted;
	}
	
	
	@Override
    public String toString() {
    	
		return "RequestData{" +
                "ReqId='" + requirementId +
                "ReqName='" + reqName +
                ", TotalTC=" + totalTC +
                ", TotalTCRun=" + totalTCRun +
                ", TotalFail =" + totalFail +
                ", TotalDefectPredicted =" + totalDefectPredicted +
                '}';
               
    }

	public int compareTo(RequestDetails reqDetail) {

		int comparePredictedFailure = ((RequestDetails) reqDetail).getTotalDefectPredicted().intValue();

		//ascending order
		//return this.runID.intValue() - compareRunID;

		//descending order
		return comparePredictedFailure - this.totalDefectPredicted.intValue();

	}
	

}
