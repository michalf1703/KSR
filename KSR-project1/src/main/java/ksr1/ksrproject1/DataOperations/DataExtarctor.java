package ksr1.ksrproject1.DataOperations;

import ksr1.ksrproject1.Article;
import ksr1.ksrproject1.DataInstance;
import ksr1.ksrproject1.FeaturesEx.*;
import ksr1.ksrproject1.KNN;
import ksr1.ksrproject1.Metrics.IMetric;
import ksr1.ksrproject1.ReadyArticle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataExtarctor {
    private ArrayList<ReadyArticle> readyArticles;
    DictionaryLoader dictionaryLoader = new DictionaryLoader();
    ArrayList<String> stopList = dictionaryLoader.loadStopList("src/main/resources/ dictionaries/stop_words.txt");
    private WCapitalFeature wCapitalFeature = new WCapitalFeature();
    private DataInstance dataInstance;
    private FeaturesExtractor featuresExtractor = new FeaturesExtractor();
    private List<String> stopWords;
    private int k;
    private IMetric metric;
    private double setDivision;
    private List<Integer> featuresIndexes;


    private int numberOfArticles;

    public DataExtarctor(int k, IMetric metric, double setDivision, List<Integer> featuresIndexes) {
        this.k = k;
        this.metric = metric;
        this.setDivision = setDivision;
        this.featuresIndexes = new ArrayList<>(featuresIndexes);
        this.readyArticles = new ArrayList<>();
        this.numberOfArticles = 0;
        this.stopWords = dictionaryLoader.loadWordsList("src/main/resources/ dictionaries/keyWords.txt");
    }

    public void incrementArticlesCount() {
        numberOfArticles++;
    }

    public int getArticlesCount() {
        return numberOfArticles;
    }

    public void readFromFile() {
        String title = "";
        String content = "";
        String place = "";
        String dateline = "";
        String topic = "";
        String exchange = "";
        //String people = "";
        Article currentArticle = new Article("", "","","", "", "", "");
        boolean flag;
        for (int i = 1; i <= 21; i++) {
            String numerPliku = String.format("%03d", i);
            String nazwaPliku = "src/main/resources/documents/reut2-" + numerPliku + ".sgm";
            try {
                String bufferLine;
                FileReader fileReader = new FileReader(nazwaPliku);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((bufferLine = bufferedReader.readLine()) != null) {
                    if (bufferLine.contains("<REUTERS ")) {
                        currentArticle = new Article("", "", "","","", "", "");
                        do {
                            flag = true;
                            int znacznikPoc = bufferLine.indexOf("<");
                            int znacznikKon = bufferLine.indexOf(">");
                            if (znacznikKon > znacznikPoc) {
                                if (znacznikKon + 1 >= bufferLine.length()) {
                                    flag = false;
                                } else {
                                    bufferLine = bufferLine.substring(znacznikKon);
                                }
                            }
                            if (flag) {
                                znacznikPoc = bufferLine.indexOf("<");
                                znacznikKon = bufferLine.indexOf(">");
                                if (znacznikKon + 1 == znacznikPoc) {
                                    bufferLine = bufferLine.substring(znacznikPoc);
                                } else {
                                    bufferLine = bufferLine.substring(znacznikPoc);
                                }
                            }
                        } while (flag);
                    }
                    if (bufferLine.contains("<TOPICS>")) {
                        int znacznikPoc = bufferLine.indexOf("<TOPICS>");
                        int znacznikKon = bufferLine.indexOf("</TOPICS>");
                        topic = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        topic = topic.replaceAll("</?D>", "");
                        currentArticle.setTopic(topic);
                    }

                    if (bufferLine.contains("<TITLE>")) {
                        int znacznikPoc = bufferLine.indexOf("<TITLE>");
                        int znacznikKon = bufferLine.indexOf("</TITLE>");
                        if (znacznikKon != -1) {
                            title = bufferLine.substring(znacznikPoc + 7, znacznikKon);
                        } else {
                            StringBuilder titleBuilder = new StringBuilder();
                            titleBuilder.append(bufferLine.substring(znacznikPoc + 7));
                            while ((bufferLine = bufferedReader.readLine()) != null) {
                                znacznikKon = bufferLine.indexOf("</TITLE>");
                                if (znacznikKon != -1) {
                                    titleBuilder.append(bufferLine, 0, znacznikKon);
                                    title = titleBuilder.toString();
                                    break;
                                } else {
                                    titleBuilder.append(bufferLine);
                                }
                            }
                        }
                        title = title.replaceAll("&lt;", "<");
                        currentArticle.setTitle(title);
                    }
                    if (bufferLine.contains("<EXCHANGES>")) {
                        int znacznikPoc = bufferLine.indexOf("<EXCHANGES>");
                        int znacznikKon = bufferLine.indexOf("</EXCHANGES>");
                        String exchanges = bufferLine.substring(znacznikPoc + 11, znacznikKon);
                        exchanges = exchanges.replaceAll("</?D>", "");
                        currentArticle.setExchange(exchanges);
                    }
                    if (bufferLine.contains("<PEOPLE>")) {
                        int znacznikPoc = bufferLine.indexOf("<PEOPLE>");
                        int znacznikKon = bufferLine.indexOf("</PEOPLE>");
                        String people = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        people = people.replaceAll("</?D>", "");
                        currentArticle.setPeople(people);
                    }


                    if (bufferLine.contains("<DATELINE>")) {
                        int znacznikkon = bufferLine.indexOf("<DATELINE>");
                        int znacznikpoc;
                        bufferLine = bufferLine.substring(znacznikkon + 10);
                        dateline = "";
                        while (!bufferLine.contains("</DATELINE>")) {
                            dateline = dateline + bufferLine + " ";
                            bufferLine = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferLine.indexOf("<");
                        dateline = dateline + bufferLine.substring(0, znacznikpoc);
                        currentArticle.setDateline(dateline);
                    }

                    if (bufferLine.contains("<PLACES>")) {
                        int znacznikPoc = bufferLine.indexOf("<PLACES>");
                        int znacznikKon = bufferLine.indexOf("</PLACES>");
                        place = bufferLine.substring(znacznikPoc + 8, znacznikKon);
                        place = place.replaceAll("</?D>", "");
                        if (isValidPlace(place)) {
                            currentArticle.setPlace(place);
                        }
                    }

                    if (bufferLine.contains("<BODY>")) {
                        int znacznikkon = bufferLine.indexOf("<BODY>");
                        int znacznikpoc;
                        bufferLine = bufferLine.substring(znacznikkon + 6);
                        content = "";
                        while (!bufferLine.contains("</BODY>")) {
                            content = content + bufferLine + " ";
                            bufferLine = bufferedReader.readLine();
                        }
                        znacznikpoc = bufferLine.indexOf("<");
                        content = content + bufferLine.substring(0, znacznikpoc);
                        content = content.replaceAll("&lt;", "<");
                        content = content.replaceAll("Reuter &#3;", "");
                        content = content.replaceAll("REUTER &#3;", "");
                        currentArticle.setBody(content);
                    }

                    if (bufferLine.contains("<TEXT TYPE=\"UNPROC\">")) {
                        StringBuilder bodyBuilder = new StringBuilder();
                        boolean isReadingBody = true;
                        while (isReadingBody && (bufferLine = bufferedReader.readLine()) != null) {
                            if (bufferLine.contains("</TEXT>")) {
                                int znacznikKon = bufferLine.indexOf("</TEXT>");
                                bodyBuilder.append(bufferLine, 0, znacznikKon);
                                isReadingBody = false;
                            } else {
                                bodyBuilder.append(bufferLine).append("\n");
                            }
                        }
                        content = bodyBuilder.toString();
                        content = content.replaceAll("&lt;", "<");
                        content = content.replaceAll("Reuter &#3;", "");
                        content = content.replaceAll("REUTER &#3;", "");
                        content = content.trim();
                        currentArticle.setBody(content);
                    }
                    if (bufferLine.contains("<TEXT TYPE=\"BRIEF\">")) {
                        StringBuilder bodyBuilder = new StringBuilder();
                        boolean isReadingBody = true;
                        while (isReadingBody && (bufferLine = bufferedReader.readLine()) != null) {
                            if (bufferLine.contains("</TEXT>")) {
                                int znacznikKon = bufferLine.indexOf("</TEXT>");
                                bodyBuilder.append(bufferLine, 0, znacznikKon);
                                isReadingBody = false;
                            } else {
                                bodyBuilder.append(bufferLine).append("\n");
                            }
                        }
                        content = bodyBuilder.toString();
                        content = content.replaceAll("&lt;", "<");
                        content = content.trim();
                        currentArticle.setBody(content);
                    }


                    if (bufferLine.contains("</REUTERS>")) {
                        if (currentArticle.getBody() != null && currentArticle.getPlace() != "") {
                            ReadyArticle readyArticle = new ReadyArticle(currentArticle);
                            readyArticles.add(readyArticle);
                            incrementArticlesCount();
                            ArrayList<String> bodyList = currentArticle.extractWordsFromBody();
                            ArrayList<String> words = dictionaryLoader.removeWordsContainedInStopList(bodyList, stopList);
                            String body = currentArticle.concatenateWords(words);
                            double wynik = wCapitalFeature.calculateWCapital(body);
                            readyArticle.setWCapital(wynik);
                        } else {
                            currentArticle = null;
                        }
                    }


                }

                bufferedReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Nie można otworzyć pliku");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        start();
    }

    private boolean isValidPlace(String place) {
        return place.equals("west-germany") ||
                place.equals("japan") ||
                place.equals("usa") ||
                place.equals("france") ||
                place.equals("uk") ||
                place.equals("canada");
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

    public void start() {
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




    }




}
