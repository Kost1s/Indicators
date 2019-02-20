package com.kap.indicators.vwap;

import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * VWAPChart.java
 *
 * Purpose: Serves as main function for running the application of getting the user time interval inputs,
 *          computing the relevant moving VWAP instances and then outputting these instances overlaid in an
 *          XY chart.
 *
 * @author Kostis
 *
**/

public class VWAPChart extends Application {

    public void start(Stage stage) {

        Map <Integer, Map<BigDecimal, BigDecimal>> multipleMovingVWAP;

        List <Integer> vwapIntervals;
        List <Trade> tradesList;

        String csvFile;

        // This is the place where all the relevant calculations
        // for multiple VWAPs are done.
        // -----------------------------------------------------

        csvFile = "/6CH6.txt";

        vwapIntervals = new TimeIntervalsList().getTimeIntervals();
        tradesList = new TradesList().getTrades(csvFile);
        multipleMovingVWAP = new MultipleMovingVWAP().getMultipleMovingVWAP(tradesList, vwapIntervals);

        // End of relevant VWAP calculations
        // ---------------------------------

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis("VWAP Values", 0.711d, 0.720d, 0.00001);
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        Scene scene = new Scene(lineChart, 800, 600);

        lineChart.setTitle("Moving VWAPs");
        xAxis.setLabel("Seconds");

        for(Map.Entry <Integer, Map<BigDecimal, BigDecimal>> timeInterval : multipleMovingVWAP.entrySet()) {

            XYChart.Series <Number, Number> series = new XYChart.Series <>();
            series.setName(String.valueOf(timeInterval.getKey()) + " Seconds VWAP");

            for(Map.Entry <BigDecimal, BigDecimal> pointInTime : timeInterval.getValue().entrySet()) {
                series.getData().add(new XYChart.Data <> (pointInTime.getKey(), pointInTime.getValue()));
            }
            lineChart.getData().add(series);
        }

            stage.setScene(scene);
            stage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}
