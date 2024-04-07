package ksr1.ksrproject1.Metrics;

import java.util.Vector;

public class Street implements IMetric {

    @Override
    public Double CalculateDistance(Vector<Object> A, Vector<Object> B) {
        Double result = 0.0;

        // Sprawdź, czy wektory mają taką samą długość
        if (A.size() != B.size()) {
            throw new IllegalArgumentException("Wektory muszą mieć taką samą długość");
        }

        // Oblicz odległość uliczną między wektorami A i B
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) instanceof Double && B.get(i) instanceof Double) {
                Double valueA = (Double) A.get(i);
                Double valueB = (Double) B.get(i);
                result += Math.abs(valueA - valueB);
            } else {
                throw new IllegalArgumentException("Wektory muszą zawierać tylko liczby Double");
            }
        }

        return result;
    }
}
