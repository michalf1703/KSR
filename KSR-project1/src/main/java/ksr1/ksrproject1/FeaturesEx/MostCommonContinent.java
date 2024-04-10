package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonContinent {
    public String countryPath = "src/main/resources/ dictionaries/continents.txt";
    DictionaryLoader dictionaryLoader = new DictionaryLoader();
    private List<String> continentList;

    public MostCommonContinent() {
        this.continentList = dictionaryLoader.loadWordsList(countryPath);
    }

    public String calculateMostCommonContinent(ArrayList<String> words) {
        Map<String, Integer> continentOccurrences = dictionaryLoader.countOccurrences(words,continentList);
        return dictionaryLoader.getMostCommon(continentOccurrences);
    }

}
