package ksr1.ksrproject1.Metrics;

import java.util.ArrayList;
import java.util.Collections;

public class Chebyshev {

    public Double calculateDistance(ArrayList<Double> A, ArrayList<Double> B) {
        ArrayList<Double> series = new ArrayList<>();

        for(int i = 0; i < A.size(); i++) {
            series.add(Math.abs(A.get(i) - B.get(i)));
        }

        return Collections.max(series);
    }
}
