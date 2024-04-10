package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MostCommonCurrency {

    public String currencyPath = "src/main/resources/ dictionaries/currency.txt";
    DictionaryLoader dictionaryLoader = new DictionaryLoader();
    private List<String> currencyList;

    public MostCommonCurrency() {
        this.currencyList = dictionaryLoader.loadWordsList(currencyPath);
    }

    public String calculateMostCommonCurrency(ArrayList<String> words) {
        Map<String, Integer> currencyOccurrences = dictionaryLoader.countOccurrences(words,currencyList);
        return dictionaryLoader.getMostCommon(currencyOccurrences);
    }

}

