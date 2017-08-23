package com.niit.defectprediction;

import org.apache.predictionio.controller.java.LJavaServing;
import scala.collection.Seq;
import com.niit.defectprediction.Query;
import com.niit.defectprediction.PredictedResult;


public class Serving extends LJavaServing<Query, PredictedResult> {

    @Override
    public PredictedResult serve(Query query, Seq<PredictedResult> predictions) {
        return predictions.head();
    }
}
