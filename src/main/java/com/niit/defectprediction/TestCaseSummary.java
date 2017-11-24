package com.niit.defectprediction;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * @author runa
 *
 */
public class TestCaseSummary{
	
	private String regWt;//0
	private String moduleQuality;//1
	private String moduleCriticality;//2
	private String runStatus;//3
	private String reqId;//4
	private String testId;//5
	private String runCycle;//6
	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param gender
	 * @param age
	 */
	public TestCaseSummary(String regWt, String moduleQuality,String moduleCriticality,String runStatus,String reqId,String testId, String runCycle){
			
		super();
		this.regWt = regWt;
		this.moduleQuality = moduleQuality;
		this.moduleCriticality = moduleCriticality;
		this.runStatus = runStatus;
		this.reqId = reqId;
		this.testId = testId;
		this.runCycle = runCycle;
	}
	
	
	
	public String getRegWt() {
		return regWt;
	}



	public void setRegWt(String regWt) {
		this.regWt = regWt;
	}



	public String getModuleQuality() {
		return moduleQuality;
	}



	public void setModuleQuality(String moduleQuality) {
		this.moduleQuality = moduleQuality;
	}



	public String getModuleCriticality() {
		return moduleCriticality;
	}



	public void setModuleCriticality(String moduleCriticality) {
		this.moduleCriticality = moduleCriticality;
	}



	public String getRunStatus() {
		return runStatus;
	}



	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}



	public String getReqId() {
		return reqId;
	}



	public void setReqId(String reqId) {
		this.reqId = reqId;
	}



	public String getTestId() {
		return testId;
	}



	public void setTestId(String testId) {
		this.testId = testId;
	}



	public String getRunCycle() {
		return runCycle;
	}



	public void setRunCycle(String runCycle) {
		this.runCycle = runCycle;
	}



	@Override
	public String toString() {
		return "TestCaseSummary [regWt=" + regWt + ", moduleQuality=" + moduleQuality
				+ ", moduleCriticality=" + moduleCriticality + ", runStatus=" + runStatus + ", reqId="
				+ reqId + ", testId=" + testId  + ", runCycle=" + runCycle + "]";
	}

	
}



class TestCaseSummaryComp implements Comparator<TestCaseSummary>{
	 
    public int compare(TestCaseSummary tc1, TestCaseSummary tc2) {
			Integer rC1 = Integer.parseInt(tc1.getRunCycle());
			Integer rc2 = Integer.parseInt(tc2.getRunCycle());
    	  
			return rC1.compareTo(rc2);
			
		}

	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
			Function<? super T, ? extends U> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T, U> Comparator<T> comparing(
			Function<? super T, ? extends U> arg0, Comparator<? super U> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingDouble(
			ToDoubleFunction<? super T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingLong(ToLongFunction<? super T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> nullsFirst(Comparator<? super T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> nullsLast(Comparator<? super T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator<TestCaseSummary> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator<TestCaseSummary> thenComparing(
			Comparator<? super TestCaseSummary> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <U extends Comparable<? super U>> Comparator<TestCaseSummary> thenComparing(
			Function<? super TestCaseSummary, ? extends U> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <U> Comparator<TestCaseSummary> thenComparing(
			Function<? super TestCaseSummary, ? extends U> arg0,
			Comparator<? super U> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator<TestCaseSummary> thenComparingDouble(
			ToDoubleFunction<? super TestCaseSummary> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator<TestCaseSummary> thenComparingInt(
			ToIntFunction<? super TestCaseSummary> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator<TestCaseSummary> thenComparingLong(
			ToLongFunction<? super TestCaseSummary> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
 
