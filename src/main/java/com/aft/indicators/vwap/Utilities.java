package com.aft.indicators.vwap;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kostis on 2/21/2017.
 */
public class Utilities {

    public static int getIndex(List<Trade> tradesList, int timeInterval) {
        long startTime = System.currentTimeMillis();

        BigDecimal timeIntervalSpecified = new BigDecimal(timeInterval);
        BigDecimal timeDiffFractionalSeconds;

        int firstElement = 0;
        int lastElement = tradesList.size();
        int midElement = 0;

        // Starting binary search
        while (firstElement <= lastElement) {

            midElement = (lastElement + firstElement)/2;
            timeDiffFractionalSeconds = BigDecimal.valueOf((tradesList.get(midElement).getTradeTime().getTime() - tradesList.get(firstElement).getTradeTime().getTime()) / 1000D);

            if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) > 0) {
                lastElement = midElement - (lastElement % 2) ;
            }

            if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) < 0){
                lastElement = tradesList.size();
                firstElement = firstElement + 1;
            }

            if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) == 0){
                int found = midElement;
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("getIndexm:" + elapsedTime);
            }

        }
        return midElement;
    }
}

