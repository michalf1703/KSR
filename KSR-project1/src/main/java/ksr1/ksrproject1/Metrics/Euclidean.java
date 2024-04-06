package ksr1.ksrproject1.Metrics;


import java.util.ArrayList;
import java.lang.Math;

public class Euclidean implements IMetric{

    @Override
    public Double CalculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        Double result = 0.0;

        for (int i = 0; i < A.size(); i++)
        {
            result += Math.pow((A.get(i) - B.get(i)), 2);
        }

        return Math.sqrt(result);
    }
}
