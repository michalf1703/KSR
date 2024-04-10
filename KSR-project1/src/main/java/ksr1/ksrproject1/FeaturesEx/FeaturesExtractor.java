package ksr1.ksrproject1.FeaturesEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public Vector<Object> extractFeatures(ArrayList<String> words,ArrayList<String> words2, double WCapital,List<Integer> featuresIndexes) {
        Vector<Object> features = new Vector<>();
        if (featuresIndexes.contains(0)) {
            features.add(keywordFrequency.calculateFKey(words));
        }
        if (featuresIndexes.contains(1)) {
            features.add(keywordFrequency.calculateFKey(words2));
        }
        if (featuresIndexes.contains(2)) {
            features.add(keywordFrequency20.calculateNKey20(words));
        }
        if (featuresIndexes.contains(3)) {
            features.add(WCapital);
        }
        if (featuresIndexes.contains(4)) {
            features.add(mostCommonCurrency.calculateMostCommonCurrency(words));
        }
        if (featuresIndexes.contains(5)) {
            features.add(mostCommonCountry.calculateMostCommonCountry(words));
        }
        if (featuresIndexes.contains(6)) {
            features.add(mostCommonAdjective.calculateMostCommonAdjective(words));
        }
        if (featuresIndexes.contains(7)) {
            features.add(mostCommonContinent.calculateMostCommonContinent(words));
        }
        if (featuresIndexes.contains(8)) {
            features.add(mostCommonSurname.calculateMostCommonSurname(words));
        }
        if (featuresIndexes.contains(9)) {
            features.add(mostCommonExchange.calculateMostCommonExchange(words));
        }
        return features;
    }




}
