package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostCommonSurname {
    DictionaryLoader dictionaryLoader = new DictionaryLoader();

    public String surnamePath = "src/main/resources/ dictionaries/people.txt";
    private List<String> surnameList;

    public MostCommonSurname() {
        this.surnameList = dictionaryLoader.loadWordsList(surnamePath);
    }

    public String calculateMostCommonSurname(ArrayList<String> words) {
        Map<String, Integer> surnameOccurrences = dictionaryLoader.countOccurrences(words,surnameList);
        return dictionaryLoader.getMostCommon(surnameOccurrences);
    }

}
