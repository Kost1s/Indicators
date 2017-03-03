package com.aft.indicators.vwap;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kostis on 2/21/2017.
 */
public class Utilities {

    public static int getIndex(List<Trade> tradesList, int timeInterval) {
        /*long startTime = System.currentTimeMillis();*/

        Long timeIntervalL = timeInterval*1000L;
        int midElement;
        int endingPoint = 0;

        for (int startingPoint = 0; startingPoint < tradesList.size(); startingPoint++) {
            int firstElement = 0;
            int lastElement = tradesList.size() - 1;
            Long endingPointTime = tradesList.get(startingPoint).getTradeTime().getTime() + timeIntervalL;

            // Starting binary search
            while (firstElement <= lastElement) {

                midElement = firstElement + (lastElement - firstElement) / 2;
                Long midElementTime = tradesList.get(midElement).getTradeTime().getTime();

                if (midElementTime > endingPointTime) {
                    lastElement = midElement - 1;
                }

                if (midElementTime < endingPointTime) {
                    firstElement = midElement + 1;
                }

                if (midElementTime.equals(endingPointTime)) {
                    endingPoint = midElement;
                    firstElement = lastElement + 1;
                    /*long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    System.out.println("getIndexm:" + elapsedTime);*/
                }

            }

        }
        return endingPoint;
    }
}

