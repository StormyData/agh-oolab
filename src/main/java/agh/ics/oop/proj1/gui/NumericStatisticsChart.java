package agh.ics.oop.proj1.gui;

import agh.ics.oop.proj1.observers.INumericDataCollectorUpdateObserver;
import agh.ics.oop.proj1.trackers.NumericDataCollector;
import javafx.application.Platform;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;

import java.util.ResourceBundle;

public class NumericStatisticsChart extends Tab implements INumericDataCollectorUpdateObserver {
    protected final XYChart.Series<Number, Number> series;
    protected int currentAge;

    NumericStatisticsChart(NumericDataCollector collector, boolean closeable) {
        setText(collector.getDesc());

        Axis<Number> xAxis = new NumberAxis();
        Axis<Number> yAxis = new NumberAxis();
        xAxis.setLabel(ResourceBundle.getBundle("Strings").getString("statisticsChart.xAxisName"));
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();

        series.setName(collector.getDesc());
        for (int i = 0; i < collector.getValues().size(); i++) {
            series.getData().add(new XYChart.Data<>(i, collector.getValues().get(i)));
        }
        chart.getData().add(series);
        setContent(chart);
        currentAge = collector.getValues().size();

        setClosable(closeable);
        collector.addObserver(this);
        setOnClosed(event -> collector.removeObserver(this));
    }

    public void addNextValue(double value) {
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(currentAge, value));
            currentAge++;
        });
    }
}
