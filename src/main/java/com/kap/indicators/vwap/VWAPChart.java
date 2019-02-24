package com.kap.indicators.vwap;

import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Konstantinos Antoniou
**/
public class VWAPChart extends Application {

    public void start(Stage stage) {
        Map <Integer, Map<BigDecimal, BigDecimal>> multipleMovingVWAP;

        String csvFile = "/6CH6.txt";
        multipleMovingVWAP = new MultipleMovingVWAP().getMultipleMovingVWAP(new TradesList().getTrades(csvFile),
                                                                            new TimeIntervalsList().getTimeIntervals());

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis("VWAP Values", 0.711d, 0.720d, 0.00001);
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        Scene scene = new Scene(lineChart, 800, 600);

        lineChart.setTitle("Moving VWAPs");
        xAxis.setLabel("Seconds");

        for(Map.Entry <Integer, Map<BigDecimal, BigDecimal>> timeInterval : multipleMovingVWAP.entrySet()) {
            XYChart.Series <Number, Number> series = new XYChart.Series <>();
            series.setName(timeInterval.getKey() + " Seconds VWAP");

            for(Map.Entry <BigDecimal, BigDecimal> pointInTime : timeInterval.getValue().entrySet()) {
                series.getData().add(new XYChart.Data <> (pointInTime.getKey(), pointInTime.getValue()));
            }
            lineChart.getData().add(series);
        }
            stage.setScene(scene);
            stage.show();
        }

    /**
     * Serves as main function for running the application of getting the user time interval inputs,
     * computing the relevant moving VWAP instances and then outputting these instances overlaid in an XY chart.
     *
     * @param args arguments passes in the method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
