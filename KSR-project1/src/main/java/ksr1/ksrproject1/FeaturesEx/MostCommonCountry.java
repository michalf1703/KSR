package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonCountry {
    public String countryPath = "src/main/resources/ dictionaries/country.txt";
    private List<String> countryList;
    DictionaryLoader dictionaryLoader = new DictionaryLoader();

    public MostCommonCountry() {
        this.countryList = dictionaryLoader.loadWordsList(countryPath);
    }

    public String calculateMostCommonCountry(ArrayList<String> words) {
        Map<String, Integer> currencyOccurrences = dictionaryLoader.countOccurrences(words,countryList);
        return dictionaryLoader.getMostCommon(currencyOccurrences);
    }




}
