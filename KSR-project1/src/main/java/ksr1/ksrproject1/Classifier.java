package ksr1.ksrproject1;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;
import ksr1.ksrproject1.DataOperations.PorterStemmer;
import ksr1.ksrproject1.FeaturesEx.FeaturesExtractor;
import ksr1.ksrproject1.Metrics.IMetric;
import ksr1.ksrproject1.charts.ResultsDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Classifier {
    private int k;
    private List<String> stopWords;
    private double precisionUK;
    private double precisionUSA;
    private double precisionJapan;
    private double precisionFrance;
    private double precisionGermany, precisionCanada;
    private double recallUSA, recallUK, recallJapan, recallFrance, recallGermany, recallCanada;
    private double accuracy;
    private double precision;
    private double recall;
    private double f1Usa, f1Uk, f1Japan, f1France, f1Germany, f1Canada;
    private double f1;


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

    public List<Double> start(ArrayList<ReadyArticle> readyArticles) {
        ArrayList<DataInstance> dataInstances = new ArrayList<>();
        for (ReadyArticle article : readyArticles) {
            article.setWords(dictionaryLoader.removeWordsContainedInStopList(article.getWords(), stopList));
            article.setWords(MakeWordsStemization(article.getWords()));
            Vector<Object> features = featuresExtractor.extractFeatures(article.getWords(),article.getTitle(),article.getWCapital(), featuresIndexes);
            String countryLabel = article.getPlace();
           // System.out.println(article.toString());
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
        precisionUK = Math.round(((double) correctUK / (correctUK + incorrectUK)) * 1000.0) / 1000.0;
        precisionUSA = Math.round(((double) correctUSA / (correctUSA + incorrectUSA)) * 1000.0) / 1000.0;
        precisionJapan = Math.round(((double) correctJapan / (correctJapan + incorrectJapan)) * 1000.0) / 1000.0;
        precisionFrance = Math.round(((double) correctFrance / (correctFrance + incorrectFrance)) * 1000.0) / 1000.0;
        precisionGermany = Math.round(((double) correctGermany / (correctGermany + incorrectGermany)) * 1000.0) / 1000.0;
        precisionCanada = Math.round(((double) correctCanada / (correctCanada + incorrectCanada)) * 1000.0) / 1000.0;
        recallUSA = Math.round(((double) correctUSA / usaSize) * 1000.0) / 1000.0;
        recallUK = Math.round(((double) correctUK / ukSize) * 1000.0) / 1000.0;
        recallJapan = Math.round(((double) correctJapan / japanSize) * 1000.0) / 1000.0;
        recallFrance = Math.round(((double) correctFrance / franceSize) * 1000.0) / 1000.0;
        recallGermany = Math.round(((double) correctGermany / germanySize) * 1000.0) / 1000.0;
        recallCanada = Math.round(((double) correctCanada / canadaSize) * 1000.0) / 1000.0;

        accuracy = Math.round(((double) correctPredictions / testSet.size()) * 1000.0) / 1000.0;

        precision = Math.round(((precisionUK*ukSize + precisionUSA*usaSize + precisionJapan*japanSize + precisionFrance*franceSize + precisionGermany*germanySize + precisionCanada*canadaSize)/testSet.size()) * 1000.0) / 1000.0;
        recall = Math.round((((recallUSA*usaSize + recallUK*ukSize + recallJapan*japanSize + recallFrance*franceSize + recallGermany*germanySize + recallCanada*canadaSize)/testSet.size())) * 1000.0) / 1000.0;
        f1Usa = Math.round((2 * (precisionUSA * recallUSA) / (precisionUSA + recallUSA)) * 1000.0) / 1000.0;
        f1Uk = Math.round((2 * (precisionUK * recallUK) / (precisionUK + recallUK)) * 1000.0) / 1000.0;
        f1Japan = Math.round((2 * (precisionJapan * recallJapan) / (precisionJapan + recallJapan)) * 1000.0) / 1000.0;
        f1France = Math.round((2 * (precisionFrance * recallFrance) / (precisionFrance + recallFrance)) * 1000.0) / 1000.0;
        f1Germany = Math.round((2 * (precisionGermany * recallGermany) / (precisionGermany + recallGermany)) * 1000.0) / 1000.0;
        f1Canada = Math.round((2 * (precisionCanada * recallCanada) / (precisionCanada + recallCanada)) * 1000.0) / 1000.0;
        f1 = Math.round(((f1Usa*usaSize + f1Uk*ukSize + f1Japan*japanSize + f1France*franceSize + f1Germany*germanySize + f1Canada*canadaSize)/testSet.size()) * 1000.0) / 1000.0;
        System.out.println("----------------------------------------------------------------");
        System.out.println("Usa.size):" + usaSize);
        System.out.println("correct+incorrect):" + (correctUSA + incorrectUSA));
        System.out.println("Dokładność klasyfikacji(accuracy): " + accuracy);
        System.out.println("Precyzja klasyfikacji dla calej klasyfikacji: " + precision);
        System.out.println("Czułość klasyfikacji dla calej klasyfikacji: " + recall);
        System.out.println("F1 dla calej klasyfikacji: " + f1);
        System.out.println("Precyzja klasyfikacji dla USA: " + precisionUSA);
        System.out.println("Precyzja klasyfikacji dla UK: " + precisionUK);
        System.out.println("Precyzja klasyfikacji dla Japan: " + precisionJapan);
        System.out.println("Precyzja klasyfikacji dla France: " + precisionFrance);
        System.out.println("Precyzja klasyfikacji dla West-Germany: " + precisionGermany);
        System.out.println("Precyzja klasyfikacji dla Canada: " + precisionCanada);
        System.out.println("Czułość klasyfikacji dla USA: " + recallUSA);
        System.out.println("Czułość klasyfikacji dla UK: " + recallUK);
        System.out.println("Czułość klasyfikacji dla Japan: " + recallJapan);
        System.out.println("Czułość klasyfikacji dla France: " + recallFrance);
        System.out.println("Czułość klasyfikacji dla West-Germany: " + recallGermany);
        System.out.println("Czułość klasyfikacji dla Canada: " + recallCanada);
        System.out.println("F1 dla USA: " + f1Usa);
        System.out.println("F1 dla UK: " + f1Uk);
        System.out.println("F1 dla Japan: " + f1Japan);
        System.out.println("F1 dla France: " + f1France);
        System.out.println("F1 dla West-Germany: " + f1Germany);
        System.out.println("F1 dla Canada: " + f1Canada);
        System.out.println("----------------------------------------------------------------");


        List<Double> results = new ArrayList<>();

        results.add(accuracy);
        results.add(precision);
        results.add(recall);
        results.add(f1);


        return results;


    }
    public String displayResults() {
        StringBuilder results = new StringBuilder();
        results.append("Dokładność klasyfikacji(accuracy): ").append(accuracy).append("%\n");
        results.append("Precyzja klasyfikacji dla calej klasyfikacji: ").append(precision).append("%\n");
        results.append("Czułość klasyfikacji dla calej klasyfikacji: ").append(recall).append("%\n");
         results.append("F1 dla calej klasyfikacji: ").append(f1).append("\n");
        results.append("Precyzja klasyfikacji dla USA: ").append(precisionUSA).append("%\n");
        results.append("Precyzja klasyfikacji dla UK: ").append(precisionUK).append("%\n");
        results.append("Precyzja klasyfikacji dla Japan: ").append(precisionJapan).append("%\n");
        results.append("Precyzja klasyfikacji dla France: ").append(precisionFrance).append("%\n");
        results.append("Precyzja klasyfikacji dla West-Germany: ").append(precisionGermany).append("%\n");
        results.append("Precyzja klasyfikacji dla Canada: ").append(precisionCanada).append("%\n");
        results.append("Czułość klasyfikacji dla USA: ").append(recallUSA).append("%\n");
        results.append("Czułość klasyfikacji dla UK: ").append(recallUK).append("%\n");
        results.append("Czułość klasyfikacji dla Japan: ").append(recallJapan).append("%\n");
        results.append("Czułość klasyfikacji dla France: ").append(recallFrance).append("%\n");
        results.append("Czułość klasyfikacji dla West-Germany: ").append(recallGermany).append("%\n");
        results.append("Czułość klasyfikacji dla Canada: ").append(recallCanada).append("%\n");
        results.append("F1 dla USA: ").append(f1Usa).append("\n");
        results.append("F1 dla UK: ").append(f1Uk).append("\n");
        results.append("F1 dla Japan: ").append(f1Japan).append("\n");
        results.append("F1 dla France: ").append(f1France).append("\n");
        results.append("F1 dla West-Germany: ").append(f1Germany).append("\n");
        results.append("F1 dla Canada: ").append(f1Canada).append("\n");
        ResultsDisplay resultsDisplay = new ResultsDisplay();
        return results.toString();
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
