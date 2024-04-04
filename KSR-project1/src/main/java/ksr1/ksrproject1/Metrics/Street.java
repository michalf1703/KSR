package ksr1.ksrproject1.Metrics;

import java.util.ArrayList;

public class Street {

    public Double calculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        Double result = 0.0;

        for (int i = 0; i < A.size(); i++) {
            result += Math.abs(A.get(i) - B.get(i));
        }

        return result;
    }
}