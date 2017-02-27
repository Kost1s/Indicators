package com.aft.indicators.vwap;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kostis on 2/21/2017.
 */
public class Utilities {

    public static int getIndex(List<Trade> tradesList, int timeInterval) {
        /*long startTime = System.currentTimeMillis();*/

        BigDecimal timeIntervalSpecified = new BigDecimal(timeInterval);
        BigDecimal timeDiffFractionalSeconds = BigDecimal.ZERO;
        int midElement = 0;

        for (int startingPoint = 0; startingPoint < tradesList.size(); startingPoint++) {
            int firstElement = 0;
            int lastElement = tradesList.size() - 1;

            // Starting binary search
            while (firstElement <= lastElement) {

                midElement = firstElement + (lastElement - firstElement) / 2;

                timeDiffFractionalSeconds = BigDecimal.valueOf((tradesList.get(midElement).getTradeTime().getTime() - tradesList.get(startingPoint).getTradeTime().getTime()) / 1000D);

                if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) > 0) {
                    lastElement = midElement - 1;
                }

                if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) < 0) {
                    firstElement = midElement + 1;
                }

                if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) == 0) {
                    int found = midElement;
                    firstElement = lastElement + 1;
                    /*long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    System.out.println("getIndexm:" + elapsedTime);*/
                }

            }

        }
        return midElement;
    }
}

