package ksr1.ksrproject1;

import ksr1.ksrproject1.DataOperations.DataExtarctor;
import ksr1.ksrproject1.Metrics.Euclidean;
import ksr1.ksrproject1.charts.AccuracyChart;

import java.util.ArrayList;
import java.util.List;

class App2
{
    public static void main( String[] args )
    {
        ArrayList<Integer> featuresIndexes = new ArrayList<>();
        featuresIndexes.add(0);
        featuresIndexes.add(1);
        featuresIndexes.add(2);
        featuresIndexes.add(3);
        featuresIndexes.add(4);
        featuresIndexes.add(5);
        featuresIndexes.add(6);
        featuresIndexes.add(7);
        featuresIndexes.add(8);
        featuresIndexes.add(9);

        int[] kValues = {1, 2, 4, 6, 9, 12, 16, 20, 25, 30};

        // Lista do przechowywania wyników accuracy
        List<Double> accuracyResults = new ArrayList<>();

        for (int k : kValues) {
            DataExtarctor dataExtarctor = new DataExtarctor();
            ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
            Euclidean metric = new Euclidean();
            Classifier classifier = new Classifier(k, metric, 0.6, featuresIndexes);
            double accuracy = classifier.start(readyArticles);

            // Dodajemy wynik accuracy do listy
            accuracyResults.add(accuracy);
        }

        AccuracyChart chart = new AccuracyChart("Accuracy Chart", "Accuracy vs k", accuracyResults);
        chart.pack();
        chart.setVisible(true);
    }
}