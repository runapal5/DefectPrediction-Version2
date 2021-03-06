package com.niit.defectprediction;

public class TestDataResult {
	private final double actual;
    private final double regwt;
    private final double reqsize;
    private final double reqquality;
    private final double predicted;
    private final double reqId;
    private final double testId;
    private final double runCycle;
    private final String reqName;
    private final double defectCount;

	

	public TestDataResult(double actual, double regwt,double reqquality ,double reqsize ,double reqId,double testId ,double runCycle,double defectCount,double predicted,String reqName){
    	this.actual = actual;
    	this.regwt= regwt;
    	this.reqsize = reqsize;
    	this.reqquality = reqquality;
    	this.predicted = predicted;
    	this.reqId = reqId;
    	this.testId = testId;
    	this.runCycle = runCycle;
    	this.reqName = reqName;
    	this.defectCount = defectCount;
    }
	

	public String getReqName() {
		return reqName;
	}



	public double getReqId() {
		return reqId;
	}


	public double getTestId() {
		return testId;
	}


	public double getRunCycle() {
		return runCycle;
	}


	public double getActual() {
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


	public double getPredicted() {
		return predicted;
	}

	public double getDefectCount() {
		return defectCount;
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
