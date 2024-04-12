package ksr1.ksrproject1.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResultsDisplay extends Application {
    private static String resultsText;

    public static void display(String results) {
        resultsText = results;
        new Thread(() -> launch()).start();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Classification Results");

        TextArea resultsArea = new TextArea(resultsText);

        VBox vbox = new VBox(resultsArea);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}