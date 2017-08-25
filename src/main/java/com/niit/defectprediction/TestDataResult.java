package com.niit.defectprediction;

public class TestDataResult {
	private final double actual;
    private final double regwt;
    private final double reqsize;
    private final double reqquality;
    private final double predicted;
    

	public TestDataResult(double actual, double regwt ,double reqsize ,double reqquality,double predicted){
    	this.actual = actual;
    	this.regwt= regwt;
    	this.reqsize = reqsize;
    	this.reqquality = reqquality;
    	this.predicted = predicted;
    }
	
	 public double getActual() {
			return actual;
		}


	public double getPredicted() {
		return predicted;
	}


	
	public double getPlan() {
		return actual;
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
    	
		return "TestDataResult{" +
                "actual='" + actual +
                ", regwt=" + regwt +
                ", reqsize=" + reqsize +
                ", reqquality=" + reqquality +
                ", predicted=" + predicted +
                '}';
               
    }

}
