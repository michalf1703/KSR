package ksr1.ksrproject1.FeaturesEx;

import java.util.ArrayList;
import java.util.List;

public class KeywordFrequency20 {

    private KeywordFrequency keywordFrequency;

    public KeywordFrequency20() {
        this.keywordFrequency = new KeywordFrequency();
    }

    public double calculateNKey20(ArrayList<String> words) {
        int totalWords = words.size();
        int twentyPercent = (int) Math.ceil(totalWords * 0.2);
        if (twentyPercent >= totalWords) {
            return (double) keywordFrequency.countKeywords(words) / totalWords;
        }
        List<String> firstTwentyPercentWords = words.subList(0, twentyPercent);
        int keywordCount = keywordFrequency.countKeywords(new ArrayList<>(firstTwentyPercentWords));
        return (double) keywordCount / twentyPercent;
    }
}
