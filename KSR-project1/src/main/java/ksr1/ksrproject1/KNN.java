package ksr1.ksrproject1;

import javafx.util.Pair;
import ksr1.ksrproject1.Metrics.IMetric;

import java.util.*;

public class KNN {
    IMetric metric;
    Integer k;
    ArrayList<Vector<Object>> featureVectors;
    ArrayList<String> countryLabels;


    public KNN(ArrayList<Vector<Object>> featureVectors,ArrayList<String> countryLabels,IMetric metric, Integer k) {
        this.metric = metric;
        this.k = k;
        this.featureVectors = featureVectors;
        this.countryLabels = countryLabels;
    }

    public String classify(Vector<Object> newFeatureVector) {
        // Tworzymy listę, która będzie przechowywać pary (odległość, etykieta kraju)
        List<Pair<Double, String>> distancesAndLabels = new ArrayList<>();

        // Obliczamy odległość między nowym wektorem cech a każdym wektorem w zbiorze treningowym
        for (int i = 0; i < featureVectors.size(); i++) {
            Vector<Object> currentFeatureVector = featureVectors.get(i);
            //double distance = metric.CalculateDistance(currentFeatureVector, newFeatureVector);
            String label = countryLabels.get(i);
            distancesAndLabels.add(new Pair<>(distance, label));
        }

        // Sortujemy listę według odległości
        Collections.sort(distancesAndLabels, Comparator.comparing(Pair::getKey));

        // Tworzymy mapę, która będzie przechowywać liczbę wystąpień etykiet krajów wśród k najbliższych sąsiadów
        Map<String, Integer> labelCounts = new HashMap<>();
        for (int i = 0; i < k; i++) {
            String label = distancesAndLabels.get(i).getValue();
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        // Wybieramy etykietę kraju, która występuje najczęściej wśród k najbliższych sąsiadów
        String mostCommonLabel = Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        return mostCommonLabel;
    }
}
