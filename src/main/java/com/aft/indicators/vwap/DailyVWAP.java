package com.aft.indicators.vwap;

import java.util.*;
import java.math.*;

/**
 *
 * com.aft.indicators.vwap.DailyVWAP.java
 *
 * Purpose: Takes a list of trades done within a day and computes the day's VWAP.
 *
 * Scope: Package local.
 *
 * @author Kostis
 *
**/

public class DailyVWAP {

    private DailyVWAP() {
        //Intentionally Blank
    }

    public static BigDecimal getDailyVWAP(List<Trade> tradesList) {

        MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);

        BigDecimal dailySumSharesNoTimesPrice = BigDecimal.ZERO;
        BigDecimal dailySharesNo = BigDecimal.ZERO;
        BigDecimal dailyVWAP;

        /**Steps in calculating VWAP:

        1. Multiply the Price by the period Volume (i.e period Shares Number). (Price x Volume)
        3. Create a Cumulative Total of the above. Cumulative(Price x Volume)
        4. Create a Cumulative Total of Volume. Cumulative(Volume)
        5. Divide the Cumulative Totals.

        VWAP = Cumulative( Price x Volume) / Cumulative(Volume) */

        for (Trade trade: tradesList) {
            dailySumSharesNoTimesPrice = dailySumSharesNoTimesPrice.add(trade.getTradeQty().multiply(trade.getTradePrice()));
            dailySharesNo = dailySharesNo.add(trade.getTradeQty());
        }

        dailyVWAP =  dailySumSharesNoTimesPrice.divide(dailySharesNo, mathContext);
        return dailyVWAP;
    }

}
