package ksr1.ksrproject1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class DataInstance {
    private Vector<Object> featureVector;
    private String countryLabel;

    public DataInstance(Vector<Object> featureVector, String countryLabel) {
        this.featureVector = featureVector;
        this.countryLabel = countryLabel;
    }

    public Vector<Object> getFeatureVector() {
        return featureVector;
    }

    public String getCountryLabel() {
        return countryLabel;
    }

    public static void shuffleDataInstances(List<DataInstance> dataInstances) {
        Collections.shuffle(dataInstances);
    }

    // Metoda do podziału danych na zbiór treningowy i testowy
    public static List<List<DataInstance>> splitDataSet(List<DataInstance> dataInstances, double trainingRatio) {
        int totalSize = dataInstances.size();
        int trainingSize = (int) (totalSize * trainingRatio);

        // Losowo przemieszaj dane
        shuffleDataInstances(dataInstances);

        // Podziel dane na zbiór treningowy i testowy
        List<DataInstance> trainingSet = dataInstances.subList(0, trainingSize);
        List<DataInstance> testSet = dataInstances.subList(trainingSize, totalSize);

        List<List<DataInstance>> result = new ArrayList<>();
        result.add(trainingSet);
        result.add(testSet);

        return result;
    }

}
