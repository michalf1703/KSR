package ksr1.ksrproject1.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class AccuracyChartSet extends ApplicationFrame {
    public AccuracyChartSet(String applicationTitle, String chartTitle, List<Double> accuracyResults) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Podział zbioru uczącego i testowego",
                "Wartość Accuracy",
                createDataset(accuracyResults),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(760, 717));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<Double> accuracyResults) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double [] setValues = {0.8, 0.6, 0.5, 0.3, 0.2};

        for (int i = 0; i < accuracyResults.size(); i++) {
            dataset.addValue(accuracyResults.get(i), "accuracy", Double.toString(setValues[i]));
        }

        return dataset;
    }
}
