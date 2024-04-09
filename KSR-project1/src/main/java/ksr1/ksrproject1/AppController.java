package ksr1.ksrproject1;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AppController {
    @FXML
    private Label welcomeText;
    @FXML
    private CheckBox KeywordIn20;

    @FXML
    private ChoiceBox<?> choiceMetric;

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
    private Slider testingSetSpinner;
    @FXML
    private Button startButton;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}