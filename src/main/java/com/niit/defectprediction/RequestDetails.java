package com.niit.defectprediction;

public class RequestDetails {
	
	private Double requirementId;
	private Integer totalTC;
	private Integer totalTCRun;
	private Integer totalFail;
	
	
	public RequestDetails(Double requirementId,Integer totalTC, Integer totalTCRun, Integer totalFail){
		
		this.requirementId = requirementId;
		this.totalTC = totalTC ;
		this.totalTCRun = totalTCRun;
		this.totalFail = totalFail;
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
	
	
	@Override
    public String toString() {
    	
		return "RequestData{" +
                "ReqId='" + requirementId +
                ", TotalTC=" + totalTC +
                ", TotalTCRun=" + totalTCRun +
                ", TotalFail =" + totalFail +
                '}';
               
    }

	
	

}
