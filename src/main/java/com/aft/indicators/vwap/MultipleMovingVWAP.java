package com.aft.indicators.vwap;

import java.math.RoundingMode;
import java.math.MathContext;
import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * com.aft.indicators.vwap.MultipleMovingVWAP.java
 *
 * Purpose: Takes a list of trades and a list of time intervals as inputs and computes the relevant moving VWAPs
 *          times series according to the time intervals specified. Time intervals unit is seconds.
 *
 * Scope: Package local
 *
 * @author Kostis
 *
**/

public class MultipleMovingVWAP {
    private static final Logger LOGGER = Logger.getLogger( TradesList.class.getName() );

    private MathContext mathContext = new MathContext(6, RoundingMode.DOWN);

    public final Map <Integer, Map<BigDecimal, BigDecimal>> getMultipleMovingVWAP(List<Trade> tradesList, List<Integer> timeIntervals) {
        Map <Integer, Map<BigDecimal, BigDecimal>> multipleMovingVWAP = new LinkedHashMap<>();

        for (int timeInterval : timeIntervals) {
            multipleMovingVWAP.put(timeInterval, getMovingVWAP(tradesList, timeInterval));
        }
        return multipleMovingVWAP;
    }

    private Map<BigDecimal, BigDecimal> getMovingVWAP (List<Trade> tradesList, int timeInterval){
        
        Map <BigDecimal, BigDecimal> movingVWAP = new LinkedHashMap<>();
        BigDecimal timeDiffFractionalSeconds;
        BigDecimal timeIntervalSpecified;
        BigDecimal timeDiffFromSecondZero;
        timeIntervalSpecified = new BigDecimal(timeInterval);

        long startTime = System.currentTimeMillis();
        int p = Utilities.getIndex(tradesList, timeInterval);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("getMovingVWAPm:" + elapsedTime);


        for (int i = 0; i < tradesList.size(); i++) {
            for (int j = i; j < tradesList.size(); j++) {

                timeDiffFractionalSeconds = BigDecimal.valueOf((tradesList.get(j).getTradeTime().getTime() - tradesList.get(i).getTradeTime().getTime())/1000D);

                if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) == 0) {
                    timeDiffFromSecondZero = BigDecimal.valueOf((tradesList.get(j).getTradeTime().getTime() - tradesList.get(0).getTradeTime().getTime())/1000D);
                    movingVWAP.put(timeDiffFromSecondZero, getTimeIntervalVWAP(tradesList, i, j));
                    j = tradesList.size() - 1;
                }
            }
        }
        return movingVWAP;
    }

    private BigDecimal getTimeIntervalVWAP (List<Trade> tradesList, int initialTimePoint, int finalTimePoint) {
        long startTime = System.currentTimeMillis();

        BigDecimal timeIntervalSumSharesNoTimesPrice;
        BigDecimal timeIntervalSharesNo;
        BigDecimal timeIntervalVWAP;

        timeIntervalSumSharesNoTimesPrice = BigDecimal.ZERO;
        timeIntervalSharesNo = BigDecimal.ZERO;

        for (int k = initialTimePoint; k <= finalTimePoint; k++) {
            timeIntervalSumSharesNoTimesPrice = timeIntervalSumSharesNoTimesPrice.add(tradesList.get(k).getTradeQty().multiply(tradesList.get(k).getTradePrice()));
            timeIntervalSharesNo = timeIntervalSharesNo.add(tradesList.get(k).getTradeQty());
        }
        timeIntervalVWAP = timeIntervalSumSharesNoTimesPrice.divide(timeIntervalSharesNo, mathContext);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("timeIntervalVWAPm:" + elapsedTime);
        return timeIntervalVWAP;
    }
}
