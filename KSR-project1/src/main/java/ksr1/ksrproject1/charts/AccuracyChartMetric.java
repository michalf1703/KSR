package ksr1.ksrproject1.charts;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.List;
import org.jfree.chart.ChartFactory;

public class AccuracyChartMetric extends ApplicationFrame {

    public AccuracyChartMetric(String applicationTitle, String chartTitle, List<List<Double>> metricsResults) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Metryka",
                "Wartość metryki",
                createDataset(metricsResults),
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerBound(0.6);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(760, 717));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset(List<List<Double>> metricsResults) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] metricValues = {"Euklides", "Czebyszew", "Uliczna"};
        String[] metricsNames = {"Accuracy", "Precision", "Recall", "F1"};

        for (int i = 0; i < metricsResults.size(); i++) {
            List<Double> metricResults = metricsResults.get(i);
            for (int j = 0; j < metricResults.size(); j++) {
                dataset.addValue(metricResults.get(j), metricsNames[j], metricValues[i]);
            }
        }

        return dataset;
    }
}