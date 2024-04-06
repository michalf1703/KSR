package ksr1.ksrproject1.FeaturesEx;

import java.util.ArrayList;
import java.util.List;

public class KeywordFrequency20 {

    private KeywordFrequency keywordFrequency;

    public KeywordFrequency20() {
        this.keywordFrequency = new KeywordFrequency();
    }

    public int calculateNKey20(ArrayList<String> words) {
        int totalWords = words.size();
        int twentyPercent = (int) Math.ceil(totalWords * 0.2);
        if (twentyPercent >= totalWords) {
            return keywordFrequency.countKeywords(words);
        }

        List<String> firstTwentyPercentWords = words.subList(0, twentyPercent);
        return keywordFrequency.countKeywords(new ArrayList<>(firstTwentyPercentWords));
    }
}
