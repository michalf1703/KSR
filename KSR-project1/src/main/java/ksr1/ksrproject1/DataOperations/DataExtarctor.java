package ksr1.ksrproject1.DataOperations;

import ksr1.ksrproject1.Article;
import ksr1.ksrproject1.DataInstance;
import ksr1.ksrproject1.FeaturesEx.*;
import ksr1.ksrproject1.KNN;
import ksr1.ksrproject1.Metrics.Chebyshev;
import ksr1.ksrproject1.Metrics.Euclidean;
import ksr1.ksrproject1.Metrics.Street;
import ksr1.ksrproject1.ReadyArticle;
import ksr1.ksrproject1.SimilarityMeasure.NGram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataExtarctor {
    private ArrayList<ReadyArticle> readyArticles;
    private KeywordFrequency keywordFrequency = new KeywordFrequency();
    private KeywordFrequency20 keywordFrequency20 = new KeywordFrequency20();
    private MostCommonCurrency mostCommonCurrency = new MostCommonCurrency();
    private MostCommonCountry mostCommonCountry = new MostCommonCountry();
    private MostCommonAdjective mostCommonAdjective = new MostCommonAdjective();
    private MostCommonContinent mostCommonContinent = new MostCommonContinent();
    private MostCommonSurname mostCommonSurname = new MostCommonSurname();
    private MostCommonExchange mostCommonExchange = new MostCommonExchange();
    ArrayList<String> stopList = loadStopList("C:\\Users\\Hp\\Documents\\GitHub\\KSR\\KSR-project1\\src\\main\\resources\\ dictionaries\\stop_words.txt");
    private WCapitalFeature wCapitalFeature = new WCapitalFeature();
    private DataInstance dataInstance;
    private FeaturesExtractor featuresExtractor = new FeaturesExtractor();
    private List<String> stopWords;


    private int numberOfArticles;

    public DataExtarctor() {
        readyArticles = new ArrayList<>();
        numberOfArticles = 0;
        stopWords = loadKeyWordsList();
    }
    private List<String> loadKeyWordsList() {
        List<String> keyList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/ dictionaries/keyWords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                keyList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyList;
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
                            ArrayList<String> words = removeWordsContainedInStopList(bodyList, stopList);
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
        displayArticles();
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

    public void displayArticles() {
        ArrayList<DataInstance> dataInstances = new ArrayList<>();
        for (ReadyArticle article : readyArticles) {
            article.setWords(removeWordsContainedInStopList(article.getWords(), stopList));
            //System.out.println(stopList.toString());
            article.setWords(MakeWordsStemization(article.getWords()));
            System.out.println(article.toString());
            double number3 = keywordFrequency.calculateFKey(article.getTitle());
            double number4 = article.getWCapital();
            ArrayList<String> title = article.getTitle();
            //System.out.println("Temat: " + title);
            System.out.println("Cecha WCapital: " + article.getWCapital());

            Vector<Object> features = featuresExtractor.extractFeatures(article.getWords());
            features.add(number3);
            features.add(number4);
            String countryLabel = article.getPlace();
          /*  for (Object feature : features) {
                System.out.println(feature);
            }*/

            DataInstance dataInstance = new DataInstance(features, countryLabel);
            dataInstances.add(dataInstance);
        }

        List<List<DataInstance>> dataSets = DataInstance.splitDataSet(dataInstances, 0.5);
        List<DataInstance> trainingSet = dataSets.get(0);
        List<DataInstance> testSet = dataSets.get(1);
        Euclidean metric = new Euclidean();
        Chebyshev metric2 = new Chebyshev();
        Street metric3 = new Street();
        for(DataInstance dataInstance : trainingSet){
            System.out.println(dataInstance.getFeatureVector());
        }
        for(DataInstance dataInstance : testSet){
            System.out.println(dataInstance.getFeatureVector());
        }
       // System.out.println("Liczba danych treningowych: " + trainingSet.size());
        //System.out.println("Liczba danych testowych: " + testSet.size());

        int k = 10; // Liczba sąsiadów
        KNN knn = new KNN(trainingSet, metric, k); // Ustaw metrykę i liczbę sąsiadów
        int correctPredictions = 0;
        for (DataInstance testData : testSet) {
            String predictedLabel = knn.classify(testData.getFeatureVector());
            String actualLabel = testData.getCountryLabel();
          //  System.out.println("Rzeczywista etykieta: " + actualLabel + ", Przewidziana etykieta: " + predictedLabel);
            if (predictedLabel.equals(actualLabel)) {
                correctPredictions++;
            }

        }
        double accuracy = (double) correctPredictions / testSet.size() * 100;
        System.out.println("Dokładność klasyfikacji: " + accuracy + "%");


    }

    private ArrayList<String> removeWordsContainedInStopList(ArrayList<String> words, ArrayList<String> stopList) {
        return words.stream()
                .filter(word -> !stopList.contains(word))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> loadStopList(String filePath) {
        ArrayList<String> stopList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                stopList.add(word);
                stopList.add(Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopList;
    }


}
