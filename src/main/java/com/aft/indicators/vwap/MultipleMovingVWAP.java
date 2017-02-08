package com.aft.indicators.vwap;

import java.math.RoundingMode;
import java.math.MathContext;
import java.math.BigDecimal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public Map <Integer, Map<BigDecimal, BigDecimal>> getMultipleMovingVWAP(List<Trade> tradesList, List<Integer> timeIntervals) {

        MathContext mathContext = new MathContext(6, RoundingMode.DOWN);

        BigDecimal timeIntervalSumSharesNoTimesPrice;
        BigDecimal timeIntervalSharesNo;
        BigDecimal timeIntervalVWAP;

        BigDecimal timeDiffFractionalSeconds;
        BigDecimal timeDiffFromSecondZero;
        BigDecimal timeIntervalSpecified;

        Map <Integer, Map<BigDecimal, BigDecimal>> multipleMovingVWAP = new LinkedHashMap<>();

        for (int timeInterval : timeIntervals) {

            timeIntervalSpecified = new BigDecimal(timeInterval);
            Map <BigDecimal, BigDecimal> movingVWAP = new LinkedHashMap<>();

            iLoop:
            for (int i = 0; i < tradesList.size(); i++) {

                for (int j = i; j < tradesList.size(); j++) {

                    timeDiffFractionalSeconds = BigDecimal.valueOf((tradesList.get(j).getTradeTime().getTime() - tradesList.get(i).getTradeTime().getTime())/1000D);

                    if (timeDiffFractionalSeconds.compareTo(timeIntervalSpecified) == 0) {

                        timeIntervalSumSharesNoTimesPrice = BigDecimal.ZERO;
                        timeIntervalSharesNo = BigDecimal.ZERO;

                        for (int k = i; k <= j; k++) {
                            timeIntervalSumSharesNoTimesPrice = timeIntervalSumSharesNoTimesPrice.add(tradesList.get(k).getTradeQty().multiply(tradesList.get(k).getTradePrice()));
                            timeIntervalSharesNo = timeIntervalSharesNo.add(tradesList.get(k).getTradeQty());
                        }

                        timeIntervalVWAP = timeIntervalSumSharesNoTimesPrice.divide(timeIntervalSharesNo, mathContext);
                        timeDiffFromSecondZero = BigDecimal.valueOf((tradesList.get(j).getTradeTime().getTime() - tradesList.get(0).getTradeTime().getTime())/1000D);

                        movingVWAP.put(timeDiffFromSecondZero, timeIntervalVWAP);

                        continue iLoop;
                    }
                }
            }
            multipleMovingVWAP.put(timeInterval, movingVWAP);
        }
        return multipleMovingVWAP;
    }

}
