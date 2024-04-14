package ksr1.ksrproject1;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private TextField fillK;
    @FXML
    private Slider podzialZbioruSlider;

    @FXML
    private Text podzialZbioruTekst;

    private String [] metrics = {"Metryka Euklidesowa", "Metryka Czebyszewa", "Metryka Uliczna"};
    private List<Integer> featuresIndexes;
    private IMetric metric;
    @FXML
    private TextField setFieldSpinner;



    @FXML
    protected void initialize() {
        choiceMetric.getItems().addAll(metrics);
        fillK.setText("5");
        featuresIndexes = new ArrayList<>();
        podzialZbioruSlider.setMin(1);
        podzialZbioruSlider.setMax(99);

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
        podzialZbioruSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double roundedValue = Math.round(newValue.doubleValue() * 100.0) / 100.0;
            podzialZbioruTekst.setText("Udział zbioru uczącego: " + roundedValue + "%");
            setFieldSpinner.setText(String.valueOf(roundedValue));
        });
        setFieldSpinner.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double enteredValue = Double.parseDouble(newValue);
                if (enteredValue >= 1 && enteredValue <= 99) {
                    podzialZbioruSlider.setValue(enteredValue);
                }
            } catch (NumberFormatException e) {

            }
        });
    }
    @FXML
    protected void onStartButtonClick() {
        int k = Integer.parseInt(fillK.getText());
        double sliderValue = podzialZbioruSlider.getValue()/100;
        DataExtarctor dataExtarctor = new DataExtarctor();
        ArrayList<ReadyArticle> readyArticles = dataExtarctor.readFromFile();
        Classifier classifier = new Classifier(k,metric,sliderValue, featuresIndexes);
        classifier.start(readyArticles);
        String result = classifier.displayResults();
        VBox vbox = new VBox(new TextArea(result));
        Scene scene = new Scene(vbox, 500, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
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