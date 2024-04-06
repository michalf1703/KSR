package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FeaturesExtractor {

    private KeywordFrequency keywordFrequency = new KeywordFrequency();
    private KeywordFrequency20 keywordFrequency20 = new KeywordFrequency20();
    private MostCommonCurrency mostCommonCurrency = new MostCommonCurrency();
    private MostCommonCountry mostCommonCountry = new MostCommonCountry();
    private MostCommonAdjective mostCommonAdjective = new MostCommonAdjective();
    private MostCommonContinent mostCommonContinent = new MostCommonContinent();
    private MostCommonSurname mostCommonSurname = new MostCommonSurname();
    private MostCommonExchange mostCommonExchange = new MostCommonExchange();
    public FeaturesExtractor() {
    }

    public Vector<Object> extractFeatures(ArrayList<String> words) {
        Vector<Object> features = new Vector<>();
        features.add(keywordFrequency.calculateFKey(words));
        features.add(keywordFrequency20.calculateNKey20(words));
        features.add(mostCommonCurrency.calculateMostCommonCurrency(words));
        features.add(mostCommonCountry.calculateMostCommonCountry(words));
        features.add(mostCommonAdjective.calculateMostCommonAdjective(words));
        features.add(mostCommonContinent.calculateMostCommonContinent(words));
        features.add(mostCommonSurname.calculateMostCommonSurname(words));
        features.add(mostCommonExchange.calculateMostCommonExchange(words));
        return features;
    }




}
