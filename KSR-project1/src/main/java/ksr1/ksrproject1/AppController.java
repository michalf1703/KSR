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
    private Label welcomeText;
    @FXML
    private CheckBox KeywordIn20;

    @FXML
    private ChoiceBox<String> choiceMetric;

    @FXML
    private TextField fillK;

    @FXML
    private CheckBox keyWordFreqFeature;

    @FXML
    private CheckBox keyWordInAllFeature;

    @FXML
    private CheckBox mostAdjectiveFeature;

    @FXML
    private CheckBox mostCountryFeatrue;

    @FXML
    private CheckBox mostCountryFeature;

    @FXML
    private CheckBox mostCurrencyFeature;

    @FXML
    private CheckBox mostExeFeatrue;

    @FXML
    private CheckBox mostKeyWordInTitleFeature;

    @FXML
    private CheckBox mostSurnameFeature;

    @FXML
    private TextField testingSetField;
    @FXML
    private Button startButton;

    private String [] metrics = {"Metryka Euklidesowa", "Metryka Czebyszewa", "Metryka Uliczna"};
    private List<Integer> featuresIndexes;
    private IMetric metric;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void initialize() {
        choiceMetric.getItems().addAll(metrics);
        choiceMetric.setValue(metrics[0]);
        fillK.setText("5");

        // Inicjalizujemy listę featuresIndexes
        featuresIndexes = new ArrayList<>();

        // Dodajemy słuchaczy do pól CheckBox
        addCheckboxListener(KeywordIn20, 0);
        addCheckboxListener(keyWordFreqFeature, 1);
        addCheckboxListener(keyWordInAllFeature, 2);
        addCheckboxListener(mostAdjectiveFeature, 3);
        addCheckboxListener(mostCountryFeatrue, 4);
        addCheckboxListener(mostCountryFeature, 5);
        addCheckboxListener(mostCurrencyFeature, 6);
        addCheckboxListener(mostExeFeatrue, 7);
        addCheckboxListener(mostKeyWordInTitleFeature, 8);
        addCheckboxListener(mostSurnameFeature, 9);

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
        DataExtarctor dataExtarctor = new DataExtarctor(k, metric, set,featuresIndexes);
        dataExtarctor.readFromFile();
        System.out.println("Liczba artykułów z odpowiednią etykietą PLACE: " + dataExtarctor.getArticlesCount());

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