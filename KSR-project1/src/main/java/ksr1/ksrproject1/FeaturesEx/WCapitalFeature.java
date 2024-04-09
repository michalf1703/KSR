package ksr1.ksrproject1.FeaturesEx;

public class WCapitalFeature {


    public WCapitalFeature() {

    }

    public double calculateWCapital(String text) {
        String[] sentences = text.split("\\.");
        int wCapitalCount = 0;
        int totalWordCount = 0;

        for (String sentence : sentences) {
            String[] words = sentence.trim().split("\\s+");
            for (int i = 1; i < words.length; i++) {
                if (Character.isUpperCase(words[i].charAt(0)) && !words[i - 1].endsWith(".")) {
                    wCapitalCount++;
                    totalWordCount++;
                }
                totalWordCount++;
            }
        }
        return (double) wCapitalCount / totalWordCount;
    }


}
