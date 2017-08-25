package com.niit.defectprediction;

import java.io.Serializable;

public class TestData implements Serializable{
	private final double plan;
    private final double regwt;
    private final double reqsize;
    private final double reqquality;
    
    public TestData(double plan, double regwt ,double reqsize ,double reqquality){
    	this.plan = plan;
    	this.regwt= regwt;
    	this.reqsize = reqsize;
    	this.reqquality = reqquality;
    }
	
    
	
	public double getPlan() {
		return plan;
	}



	public double getRegwt() {
		return regwt;
	}



	public double getReqsize() {
		return reqsize;
	}



	public double getReqquality() {
		return reqquality;
	}



	@Override
    public String toString() {
    	
		return "TestData{" +
                "plan='" + plan +
                ", regwt=" + regwt +
                ", reqsize=" + reqsize +
                ", reqquality=" + reqquality +
                '}';
               
    }

}
