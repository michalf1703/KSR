package ksr1.ksrproject1.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.List;

public class AccuracyChartFeatures extends ApplicationFrame {

    public AccuracyChartFeatures(String applicationTitle, String chartTitle, List<List<Double>> metricsResults) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Zbiór cech",
                "Wartość miary jakości klasyfikacji",
                createDataset(metricsResults),
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(0.4);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(760, 717));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<List<Double>> metricsResults) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] featuresValues = {"z1", "z2", "z3", "z4"};
        String[] metricsNames = {"Accuracy", "Precision", "Recall", "F1"};

        for (int i = 0; i < featuresValues.length; i++) {
            List<Double> featureResults = metricsResults.get(i);
            for (int j = 0; j < featureResults.size(); j++) {
                dataset.addValue(featureResults.get(j), metricsNames[j], featuresValues[i]);
            }
        }
        return dataset;
    }
}