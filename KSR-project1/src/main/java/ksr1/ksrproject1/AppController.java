package ksr1.ksrproject1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ksr1.ksrproject1.DataOperations.DataExtarctor;
import ksr1.ksrproject1.Metrics.Chebyshev;
import ksr1.ksrproject1.Metrics.Euclidean;
import ksr1.ksrproject1.Metrics.IMetric;
import ksr1.ksrproject1.Metrics.Street;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML
    private CheckBox KeywordIn20, keyWordFreqFeature, keyWordInAllFeature, mostAdjectiveFeature,
            mostCountryFeatrue, mostContinentFeature, mostCurrencyFeature,
            mostExeFeatrue, mostKeyWordInTitleFeature, mostSurnameFeature;

    @FXML
    private ChoiceBox<String> choiceMetric;

    @FXML
    private TextField fillK, testingSetField;

    private String [] metrics = {"Metryka Euklidesowa", "Metryka Czebyszewa", "Metryka Uliczna"};
    private List<Integer> featuresIndexes;
    private IMetric metric;


    @FXML
    protected void initialize() {
        choiceMetric.getItems().addAll(metrics);
        fillK.setText("5");

        // Inicjalizujemy listę featuresIndexes
        featuresIndexes = new ArrayList<>();

        // Dodajemy słuchaczy do pól CheckBox
        addCheckboxListener(keyWordInAllFeature, 0);
        addCheckboxListener(keyWordFreqFeature, 1);
        addCheckboxListener(mostCurrencyFeature, 2);
        addCheckboxListener(KeywordIn20, 3);
        addCheckboxListener(mostAdjectiveFeature, 4);
        addCheckboxListener(mostCountryFeatrue, 5);
        addCheckboxListener(mostSurnameFeature, 6);
        addCheckboxListener(mostExeFeatrue, 7);
        addCheckboxListener(mostContinentFeature, 8);
        addCheckboxListener(mostKeyWordInTitleFeature, 9);


        // Dodajemy słuchacza do pola ChoiceBox
        choiceMetric.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Metryka Euklidesowa":
                    metric = new Euclidean();
                    break;
                case "Metryka Czebyszewa":
                    metric = new Chebyshev();
                    break;
                case "Metryka Uliczna":
                    metric = new Street();
                    break;
            }
        });
    }
    @FXML
    protected void onStartButtonClick() {
        int k = Integer.parseInt(fillK.getText());
        double set = Double.parseDouble(testingSetField.getText());
        DataExtarctor dataExtarctor = new DataExtarctor();
        ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
        Classifier classifier = new Classifier(k,metric,set, featuresIndexes);
        classifier.start(readyArticles);
       // System.out.println("Liczba artykułów z odpowiednią etykietą PLACE: " + dataExtarctor.getArticlesCount());

    }

    private void addCheckboxListener(CheckBox checkbox, int index) {
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                featuresIndexes.add(index);
            } else {
                featuresIndexes.remove(Integer.valueOf(index));
            }
        });
    }

}