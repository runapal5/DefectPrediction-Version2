package com.niit.defectprediction;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;

import io.prediction.EngineClient;

public class PredictionClient {

	public static void main(String[] args) {
		EngineClient engineClient = new EngineClient("http://172.18.51.88:8000/");
		try {
			JsonObject responseObject = engineClient.sendQuery(ImmutableMap.<String, Object>of(
			        "plan", 1,
			        "regwt",  2,
			        "reqsize", 1,
			        "reqquality",3
			    ));
			
			System.out.println(responseObject.toString());
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
