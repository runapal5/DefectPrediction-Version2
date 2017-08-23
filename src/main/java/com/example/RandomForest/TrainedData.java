package com.example.RandomForest;

public class TrainedData {
	
	double actual;
	double regWt;
	double reqSize;
	double reqQuality;
	double prediction;
	
	
	public TrainedData(double actual,double regWt ,double reqSize ,double reqQuality , double prediction)
	{
		super();
		this.actual = actual;
		this.regWt = regWt;
		this.reqSize = reqSize;
		this.reqQuality = reqQuality;
		this.prediction = prediction;
	}
	
	
	public double getActual() {
		return actual;
	}
	public void setActual(double actual) {
		this.actual = actual;
	}
	public double getRegWt() {
		return regWt;
	}
	public void setRegWt(double regWt) {
		this.regWt = regWt;
	}
	public double getReqSize() {
		return reqSize;
	}
	public void setReqSize(double reqSize) {
		this.reqSize = reqSize;
	}
	public double getReqQuality() {
		return reqQuality;
	}
	public void setReqQuality(double reqQuality) {
		this.reqQuality = reqQuality;
	}
	public double getPrediction() {
		return prediction;
	}
	public void setPrediction(double prediction) {
		this.prediction = prediction;
	}
	
	@Override
    public String toString() {

        return "TrainedData [actual=" + actual + ", regWt=" + regWt
	                + ", reqSize=" + reqSize + ", reqQuality=" + reqQuality + ", prediction="+ prediction + "]";
            
    }


}
