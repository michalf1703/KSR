package ksr1.ksrproject1.FeaturesEx;

import ksr1.ksrproject1.DataOperations.DictionaryLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MostCommonAdjective {
    public String adjectivePath = "src/main/resources/ dictionaries/adjective.txt";
    private List<String> adjectiveList;
    DictionaryLoader dictionaryLoader = new DictionaryLoader();

    public MostCommonAdjective() {
        this.adjectiveList = dictionaryLoader.loadWordsList(adjectivePath);
    }

    public String calculateMostCommonAdjective(ArrayList<String> words) {
        Map<String, Integer> adjectivesOccurrences = dictionaryLoader.countOccurrences(words,adjectiveList);
        return dictionaryLoader.getMostCommon(adjectivesOccurrences);
    }



}
