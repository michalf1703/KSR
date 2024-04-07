package ksr1.ksrproject1;

import javafx.util.Pair;
import ksr1.ksrproject1.DataInstance;
import ksr1.ksrproject1.Metrics.IMetric;

import java.util.*;

public class KNN {
    IMetric metric;
    Integer k;
    List<DataInstance> trainingSet;

    public KNN(List<DataInstance> trainingSet, IMetric metric, Integer k) {
        this.metric = metric;
        this.k = k;
        this.trainingSet = trainingSet;
    }

    public String classify(Vector<Object> newFeatureVector) {
        List<Pair<Double, String>> distancesAndLabels = new ArrayList<>();

        for (DataInstance dataInstance : trainingSet) {
            Vector<Object> currentFeatureVector = dataInstance.getFeatureVector();
            double distance = metric.CalculateDistance(currentFeatureVector, newFeatureVector);
            String label = dataInstance.getCountryLabel();
            distancesAndLabels.add(new Pair<>(distance, label));
        }
        Collections.sort(distancesAndLabels, Comparator.comparing(Pair::getKey));
        Map<String, Integer> labelCounts = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String label = distancesAndLabels.get(i).getValue();
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        String mostCommonLabel = Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        return mostCommonLabel;
    }
}
