package ksr1.ksrproject1;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;
import ksr1.ksrproject1.DataOperations.PorterStemmer;
import ksr1.ksrproject1.FeaturesEx.FeaturesExtractor;
import ksr1.ksrproject1.Metrics.IMetric;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Classifier {
    private int k;
    private List<String> stopWords;


    public Classifier(int k, IMetric metric, double setDivision, List<Integer> featuresIndexes) {
        this.k = k;
        this.metric = metric;
        this.setDivision = setDivision;
        this.featuresIndexes = new ArrayList<>(featuresIndexes);
        this.readyArticles = new ArrayList<>();
        this.stopWords = dictionaryLoader.loadWordsList("src/main/resources/ dictionaries/keyWords.txt");
    }

    private IMetric metric;
    private double setDivision;
    private List<Integer> featuresIndexes;
    private ArrayList<ReadyArticle> readyArticles;
    private DictionaryLoader dictionaryLoader = new DictionaryLoader();
    ArrayList<String> stopList = dictionaryLoader.loadStopList("src/main/resources/ dictionaries/stop_words.txt");
    private FeaturesExtractor featuresExtractor = new FeaturesExtractor();

    public double start(ArrayList<ReadyArticle> readyArticles) {
        ArrayList<DataInstance> dataInstances = new ArrayList<>();
        for (ReadyArticle article : readyArticles) {
            article.setWords(dictionaryLoader.removeWordsContainedInStopList(article.getWords(), stopList));
            article.setWords(MakeWordsStemization(article.getWords()));
            Vector<Object> features = featuresExtractor.extractFeatures(article.getWords(),article.getTitle(),article.getWCapital(), featuresIndexes);
            String countryLabel = article.getPlace();

            DataInstance dataInstance = new DataInstance(features, countryLabel);
            dataInstances.add(dataInstance);
        }

        List<List<DataInstance>> dataSets = DataInstance.splitDataSet(dataInstances, setDivision);
        List<DataInstance> trainingSet = dataSets.get(0);
        List<DataInstance> testSet = dataSets.get(1);
        for(DataInstance dataInstance : trainingSet){
            System.out.println(dataInstance.getFeatureVector());
        }
        for(DataInstance dataInstance : testSet){
            System.out.println(dataInstance.getFeatureVector());
        }
        // System.out.println("Liczba danych treningowych: " + trainingSet.size());
        //System.out.println("Liczba danych testowych: " + testSet.size());

        KNN knn = new KNN(trainingSet, metric, k); // Ustaw metrykę i liczbę sąsiadów
        int correctPredictions = 0;
        int correctUSA = 0;
        int incorrectUSA = 0;
        int correctUK = 0;
        int incorrectUK = 0;
        int correctJapan = 0;
        int incorrectJapan= 0;
        int correctFrance = 0;
        int incorrectFrance = 0;
        int correctGermany = 0;
        int incorrectGermany = 0;
        int correctCanada = 0;
        int incorrectCanada = 0;
        int usaSize =0;
        int ukSize =0;
        int japanSize =0;
        int franceSize =0;
        int germanySize =0;
        int canadaSize =0;

        for (DataInstance testData : testSet) {
            String predictedLabel = knn.classify(testData.getFeatureVector());
            String actualLabel = testData.getCountryLabel();
            // System.out.println("Rzeczywista etykieta: " + actualLabel + ", Przewidziana etykieta: " + predictedLabel);
            if (predictedLabel.equals(actualLabel)) {
                correctPredictions++;
            }
            if (predictedLabel.equals("usa") && actualLabel.equals("usa")){
                correctUSA++;
            }
            if (predictedLabel.equals("usa") && !actualLabel.equals("usa")){
                incorrectUSA++;
            }
            if (predictedLabel.equals("uk") && actualLabel.equals("uk")){
                correctUK++;
            }
            if (predictedLabel.equals("uk") && !actualLabel.equals("uk")){
                incorrectUK++;
            }
            if (predictedLabel.equals("japan") && actualLabel.equals("japan")){
                correctJapan++;
            }
            if (predictedLabel.equals("japan") && !actualLabel.equals("japan")){
                incorrectJapan++;
            }
            if (predictedLabel.equals("france") && actualLabel.equals("france")){
                correctFrance++;
            }
            if (predictedLabel.equals("france") && !actualLabel.equals("france")){
                incorrectFrance++;
            }
            if (predictedLabel.equals("west-germany") && actualLabel.equals("west-germany")){
                correctGermany++;
            }
            if (predictedLabel.equals("west-germany") && !actualLabel.equals("west-germany")){
                incorrectGermany++;
            }
            if (predictedLabel.equals("canada") && actualLabel.equals("canada")){
                correctCanada++;
            }
            if (predictedLabel.equals("canada") && !actualLabel.equals("canada")){
                incorrectCanada++;
            }
            //precision dla calej klasyfikacji
            if (actualLabel.equals("usa")) {
                usaSize++;
            }
            if (actualLabel.equals("uk")) {
                ukSize++;
            }
            if (actualLabel.equals("japan")) {
                japanSize++;
            }
            if (actualLabel.equals("france")) {
                franceSize++;
            }
            if (actualLabel.equals("west-germany")) {
                germanySize++;
            }
            if (actualLabel.equals("canada")) {
                canadaSize++;
            }
        }
        double precisionUK = (double) correctUK / (correctUK + incorrectUK) * 100;
        double precisionUSA = (double) correctUSA / (correctUSA + incorrectUSA) * 100;
        double precisionJapan = (double) correctJapan / (correctJapan + incorrectJapan) * 100;
        double precisionFrance = (double) correctFrance / (correctFrance + incorrectFrance) * 100;
        double precisionGermany = (double) correctGermany / (correctGermany + incorrectGermany) * 100;
        double precisionCanada = (double) correctCanada / (correctCanada + incorrectCanada) * 100;
        double recallUSA = (double) correctUSA / usaSize * 100;
        double recallUK = (double) correctUK / ukSize * 100;
        double recallJapan = (double) correctJapan / japanSize * 100;
        double recallFrance = (double) correctFrance / franceSize * 100;
        double recallGermany = (double) correctGermany / germanySize * 100;
        double recallCanada = (double) correctCanada / canadaSize * 100;
        double accuracy = (double) correctPredictions / testSet.size() * 100;
        double precision = (precisionUK*ukSize + precisionUSA*usaSize + precisionJapan*japanSize + precisionFrance*franceSize + precisionGermany*germanySize + precisionCanada*canadaSize)/testSet.size();
        double recall = ((recallUSA*usaSize + recallUK*ukSize + recallJapan*japanSize + recallFrance*franceSize + recallGermany*germanySize + recallCanada*canadaSize)/testSet.size());
        double f1Usa = 2 * (precisionUSA * recallUSA) / (precisionUSA + recallUSA);
        double f1Uk = 2 * (precisionUK * recallUK) / (precisionUK + recallUK);
        double f1Japan = 2 * (precisionJapan * recallJapan) / (precisionJapan + recallJapan);
        double f1France = 2 * (precisionFrance * recallFrance) / (precisionFrance + recallFrance);
        double f1Germany = 2 * (precisionGermany * recallGermany) / (precisionGermany + recallGermany);
        double f1Canada = 2 * (precisionCanada * recallCanada) / (precisionCanada + recallCanada);
        double f1 = (f1Usa*usaSize + f1Uk*ukSize + f1Japan*japanSize + f1France*franceSize + f1Germany*germanySize + f1Canada*canadaSize)/testSet.size();
        System.out.println("Dokładność klasyfikacji(accuracy): " + accuracy + "%");
        System.out.println("Precyzja klasyfikacji dla calej klasyfikacji: " + precision + "%");
        System.out.println("Czułość klasyfikacji dla calej klasyfikacji: " + recall + "%");
        System.out.println("F1 dla calej klasyfikacji: " + f1);
        System.out.println("Precyzja klasyfikacji dla USA: " + precisionUSA + "%");
        System.out.println("Precyzja klasyfikacji dla UK: " + precisionUK + "%");
        System.out.println("Precyzja klasyfikacji dla Japan: " + precisionJapan + "%");
        System.out.println("Precyzja klasyfikacji dla France: " + precisionFrance + "%");
        System.out.println("Precyzja klasyfikacji dla West-Germany: " + precisionGermany + "%");
        System.out.println("Precyzja klasyfikacji dla Canada: " + precisionCanada + "%");
        System.out.println("Czułość klasyfikacji dla USA: " + recallUSA + "%");
        System.out.println("Czułość klasyfikacji dla UK: " + recallUK + "%");
        System.out.println("Czułość klasyfikacji dla Japan: " + recallJapan + "%");
        System.out.println("Czułość klasyfikacji dla France: " + recallFrance + "%");
        System.out.println("Czułość klasyfikacji dla West-Germany: " + recallGermany + "%");
        System.out.println("Czułość klasyfikacji dla Canada: " + recallCanada + "%");
        System.out.println("F1 dla USA: " + f1Usa);
        System.out.println("F1 dla UK: " + f1Uk);
        System.out.println("F1 dla Japan: " + f1Japan);
        System.out.println("F1 dla France: " + f1France);
        System.out.println("F1 dla West-Germany: " + f1Germany);
        System.out.println("F1 dla Canada: " + f1Canada);
        return accuracy;
    }

    public ArrayList<String> MakeWordsStemization(ArrayList<String> words) {
        PorterStemmer stemmer;
        ArrayList<String> stemmWords = new ArrayList<>();
        String stemmWord;

        for (String w : words) {
            if (stopWords.contains(w)) {
                stemmWords.add(w);
            } else {
                stemmer = new PorterStemmer();
                stemmer.add(w.toCharArray(), w.length());
                stemmer.stem();
                stemmWord = stemmer.toString();
                if (stemmWord.length() > 1) {
                    stemmWords.add(stemmWord);
                }
            }
        }

        return stemmWords;
    }
}
