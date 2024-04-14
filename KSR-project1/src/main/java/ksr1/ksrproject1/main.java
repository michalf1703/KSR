package ksr1.ksrproject1;

import ksr1.ksrproject1.DataOperations.DataExtarctor;
import ksr1.ksrproject1.Metrics.Chebyshev;
import ksr1.ksrproject1.Metrics.Euclidean;
import ksr1.ksrproject1.Metrics.IMetric;
import ksr1.ksrproject1.Metrics.Street;
import ksr1.ksrproject1.charts.AccuracyChartFeatures;
import ksr1.ksrproject1.charts.AccuracyChartK;
import ksr1.ksrproject1.charts.AccuracyChartMetric;
import ksr1.ksrproject1.charts.AccuracyChartSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class App2
{
    public static void main( String[] args ) {
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

        int[] kValues = {1, 4, 8, 14};

        // Lista do przechowywania wyników accuracy
        List<List<Double>> accuracyResults = new ArrayList<>();
        List<List<Double>> accuracyResults2 = new ArrayList<>();
        List<List<Double>> accuracyResults3 = new ArrayList<>();

        for (int k : kValues) {
            DataExtarctor dataExtarctor = new DataExtarctor();
            ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
            Street metric = new Street();
            Classifier classifier = new Classifier(k, metric, 0.5, featuresIndexes);
            List<Double> results = classifier.start(readyArticles);

            // Dodajemy wynik accuracy do listy
            accuracyResults.add(results);
        }

        AccuracyChartK chart = new AccuracyChartK("Accuracy Chart", "Zależność wartości miary jakości klasyfikacji od wartości parametru k", accuracyResults);
        chart.pack();
        chart.setVisible(true);

        double[] setValues = {0.7, 0.5, 0.3};

        for (double set : setValues) {
            DataExtarctor dataExtarctor = new DataExtarctor();
            ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
            Street metric = new Street();
            Classifier classifier = new Classifier(4, metric, set, featuresIndexes);
            List<Double> results = classifier.start(readyArticles);

            // Dodajemy wynik accuracy do listy
            accuracyResults2.add(results);
        }

        AccuracyChartSet chart2 = new AccuracyChartSet("Accuracy Chart", "Zależność wartości miary jakości klasyfikacji od podziału zbiorów", accuracyResults2);
        chart2.pack();
        chart2.setVisible(true);


        List<IMetric> metrics = Arrays.asList(new Euclidean(), new Chebyshev(), new Street());

        for (IMetric metric : metrics) {
            DataExtarctor dataExtarctor = new DataExtarctor();
            ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
            Classifier classifier = new Classifier(4, metric, 0.5, featuresIndexes);
            List<Double> results = classifier.start(readyArticles);

            // Dodajemy wynik accuracy do listy
            accuracyResults3.add(results);
        }

        AccuracyChartMetric chart3 = new AccuracyChartMetric("Accuracy Chart", "Zależność wartości miary jakości klasyfikacji od wybranej metryki", accuracyResults3);
        chart3.pack();
        chart3.setVisible(true);


        //zbior samych liczbowych cech
        ArrayList<Integer> featuresIndexes2 = new ArrayList<>();
        featuresIndexes2.add(0); //udzial slow kluczowych w tekscie
        featuresIndexes2.add(1); //udzial slow z wielkiej litery
        featuresIndexes2.add(3); //slowa kluczowe w 20% tekstu
        featuresIndexes2.add(9); //udzial slow kluczowych w tytule tekstu

        //zbior samych kategorialnych cech
        ArrayList<Integer> featuresIndexes3 = new ArrayList<>();
        featuresIndexes3.add(2); //waluta
        featuresIndexes3.add(4); //przymitonik
        featuresIndexes3.add(5); //kraj
        featuresIndexes3.add(6); //nazwisko
        featuresIndexes3.add(7); //gielda
        featuresIndexes3.add(8); //kontynent

        //zbior cech liczbowych i kategorialnych -najlepsze
        ArrayList<Integer> featuresIndexes4 = new ArrayList<>();
        featuresIndexes4.add(0); //udzial slow kluczowych w tekscie
        featuresIndexes4.add(2); //waluta
        featuresIndexes4.add(3); //slowa kluczowe w 20% tekstu
        featuresIndexes4.add(4); //przymitonik
        featuresIndexes4.add(5); //kraj
        featuresIndexes4.add(9); //udzial slow kluczowych w tytule tekstu

        //chyba najmniejznaczace cechy
        ArrayList<Integer> featuresIndexes5 = new ArrayList<>();
        featuresIndexes5.add(1); //udzial slow z wielkiej litery
        featuresIndexes5.add(6); //nazwisko
        featuresIndexes5.add(7); //gielda
        featuresIndexes5.add(8); //kontynent

        List<ArrayList<Integer>> featuresIndexesList = Arrays.asList(featuresIndexes2, featuresIndexes3, featuresIndexes4, featuresIndexes5);
        List<List<Double>> accuracyResults4 = new ArrayList<>();
        for (ArrayList<Integer> currentFeaturesIndexes : featuresIndexesList) {
                DataExtarctor dataExtarctor = new DataExtarctor();
                ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
                Street metric = new Street();
                // Używamy aktualnego zestawu cech do utworzenia klasyfikatora
                Classifier classifier = new Classifier(4, metric, 0.5, currentFeaturesIndexes);
                List<Double> results = classifier.start(readyArticles);

                // Dodajemy wynik accuracy do listy
                accuracyResults4.add(results);
               // classifier.displayResults();
            System.out.println(dataExtarctor.getArticlesCount());
        }

        AccuracyChartFeatures chart4 = new AccuracyChartFeatures("Accuracy Chart", "Zależność wartości miary jakości klasyfikacji od zbioru cech", accuracyResults4);
        chart4.pack();
        chart4.setVisible(true);


    }
}
