package ksr1.ksrproject1.Metrics;

import ksr1.ksrproject1.SimilarityMeasure.NGram;

import java.util.Vector;

public class Euclidean implements IMetric {

    @Override
    public Double CalculateDistance(Vector<Object> A, Vector<Object> B) {
        Double result = 0.0;
        if (A.size() != B.size()) {
            throw new IllegalArgumentException("Wektory muszą mieć taką samą długość");
        }
        for (int i = 0; i < A.size(); i++) {
            Object valueA = A.get(i);
            Object valueB = B.get(i);

            if (valueA != null && valueB != null) {
                if (valueA instanceof Double && valueB instanceof Double) {
                    Double doubleA = (Double) valueA;
                    Double doubleB = (Double) valueB;
                    result += Math.pow((doubleA - doubleB), 2);
                } else {
                    NGram nGram = new NGram();
                    String wordA = valueA.toString();
                    String wordB = valueB.toString();
                    result += Math.pow((nGram.calculateSimilarity(wordA, wordB)), 2);
                }
            }
        }
        //System.out.println("Euclidean distance: " + Math.sqrt(result));
        return Math.sqrt(result);
    }
}
