package ksr1.ksrproject1.Metrics;

import ksr1.ksrproject1.SimilarityMeasure.NGram;

import java.util.Vector;

public class Chebyshev implements IMetric {

    @Override
    public Double CalculateDistance(Vector<Object> A, Vector<Object> B) {
        Double maxDifference = 0.0;
        if (A.size() != B.size()) {
            throw new IllegalArgumentException("Wektory muszą mieć taką samą długość");
        }
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) instanceof Double && B.get(i) instanceof Double) {
                Double valueA = (Double) A.get(i);
                Double valueB = (Double) B.get(i);
                Double difference = Math.abs(valueA - valueB);
                if (difference > maxDifference) {
                    maxDifference = difference;
                }
            } else if (A.get(i) != null && B.get(i) != null) {
                NGram nGram = new NGram();
                String wordA = A.get(i).toString();
                String wordB = B.get(i).toString();
                Double similarity = nGram.calculateSimilarity(wordA, wordB);
                if (similarity > maxDifference) {
                    maxDifference = similarity;
                }
            }
        }

        return maxDifference;
    }
}
