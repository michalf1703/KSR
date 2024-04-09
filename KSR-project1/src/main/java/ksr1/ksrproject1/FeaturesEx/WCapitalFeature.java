package ksr1.ksrproject1.FeaturesEx;

import java.util.ArrayList;
import java.util.List;

public class WCapitalFeature {


    public WCapitalFeature() {

    }

    public double calculateWCapital(String text) {
        // Podziel tekst na zdania
        String[] sentences = text.split("\\.");

        // Inicjalizuj licznik słów rozpoczynających się wielką literą
        int wCapitalCount = 0;
        int totalWordCount = 0;

        for (String sentence : sentences) {
            // Podziel zdanie na słowa
            String[] words = sentence.trim().split("\\s+");
            for (int i = 1; i < words.length; i++) {
                // Sprawdź, czy słowo rozpoczyna się wielką literą i nie jest pierwszym słowem w zdaniu
                if (Character.isUpperCase(words[i].charAt(0)) && !words[i - 1].endsWith(".")) {
                    wCapitalCount++;
                    totalWordCount++;
                }
                totalWordCount++;
            }
        }
        System.out.println("liczba słów zaczynających się wielką literą: " + wCapitalCount);
        System.out.println("liczba wszystkich słów: " + totalWordCount);
        System.out.println("WCapital: " + (double) wCapitalCount / totalWordCount);
        return (double) wCapitalCount / totalWordCount;
    }


}
