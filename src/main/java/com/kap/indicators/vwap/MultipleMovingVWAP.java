package com.kap.indicators.vwap;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Konstantinos Antoniou
 */
public class MultipleMovingVWAP {

    private final MathContext mathContext = new MathContext(6, RoundingMode.DOWN);

    public final Map<Integer, Map<BigDecimal, BigDecimal>> getMultipleMovingVWAP(List<Trade> tradesList,
                                                                                 List<Integer> timeIntervals) {
        Map<Integer, Map<BigDecimal, BigDecimal>> multipleMovingVWAP = new LinkedHashMap<>();

        for (int timeInterval : timeIntervals) {
            multipleMovingVWAP.put(timeInterval, getMovingVWAP(tradesList, timeInterval));
        }
        return multipleMovingVWAP;
    }

    private Map<BigDecimal, BigDecimal> getMovingVWAP(List<Trade> tradesList, int timeInterval) {
        Map<BigDecimal, BigDecimal> movingVWAP = new LinkedHashMap<>();
        long timeIntervalL = timeInterval * 1000L;
        BigDecimal timeDiffFromSecondZero;

        int endingPoint;
        Long endingPointTime;
        for (int startingPoint = 0; startingPoint < tradesList.size(); startingPoint++) {
            endingPointTime = tradesList.get(startingPoint).getTradeTime().getTime() + timeIntervalL;
            endingPoint = getEndingPoint(tradesList, endingPointTime);

            if (endingPoint != -1) {
                timeDiffFromSecondZero = BigDecimal.valueOf((tradesList.get(endingPoint).getTradeTime().getTime() -
                                                             tradesList.get(0).getTradeTime().getTime()) / 1000D);
                movingVWAP.put(timeDiffFromSecondZero, getTimeIntervalVWAP(tradesList, startingPoint, endingPoint));
            }
        }
        return movingVWAP;
    }

    private BigDecimal getTimeIntervalVWAP(List<Trade> tradesList, int initialTimePoint, int finalTimePoint) {
        BigDecimal timeIntervalSumSharesNoTimesPrice = BigDecimal.ZERO;
        BigDecimal timeIntervalSharesNo = BigDecimal.ZERO;
        for (int k = initialTimePoint; k <= finalTimePoint; k++) {
            timeIntervalSumSharesNoTimesPrice = timeIntervalSumSharesNoTimesPrice.add(
                    tradesList.get(k).getTradeQty().multiply(tradesList.get(k).getTradePrice()));
            timeIntervalSharesNo = timeIntervalSharesNo.add(tradesList.get(k).getTradeQty());
        }
        return timeIntervalSumSharesNoTimesPrice.divide(timeIntervalSharesNo, mathContext);
    }

    private int getEndingPoint(List<Trade> tradesList, Long endingPointTime) {
        int midElement;
        int firstElement = 0;
        int lastElement = tradesList.size() - 1;

        while (firstElement <= lastElement) {

            midElement = firstElement + ((lastElement - firstElement) / 2);
            Long midElementTime = tradesList.get(midElement).getTradeTime().getTime();

            if (midElementTime > endingPointTime) {
                lastElement = midElement - 1;
            }

            if (midElementTime < endingPointTime) {
                firstElement = midElement + 1;
            }

            if (midElementTime.equals(endingPointTime)) {
                firstElement = lastElement + 1;
                return midElement;
            }

        }
        return -1;
    }

}