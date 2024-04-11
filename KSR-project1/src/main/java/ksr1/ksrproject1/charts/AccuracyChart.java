package ksr1.ksrproject1.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.List;
import org.jfree.chart.ChartFactory;


public class AccuracyChart extends ApplicationFrame {

    public AccuracyChart(String applicationTitle, String chartTitle, List<Double> accuracyResults) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Wartość k",
                "Accuracy",
                createDataset(accuracyResults),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<Double> accuracyResults) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[] kValues = {1, 2, 4, 6, 9, 12, 16, 20, 25, 30};

        for (int i = 0; i < accuracyResults.size(); i++) {
            dataset.addValue(accuracyResults.get(i), "accuracy", Integer.toString(kValues[i]));
        }

        return dataset;
    }
}