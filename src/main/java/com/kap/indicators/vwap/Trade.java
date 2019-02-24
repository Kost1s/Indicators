package com.kap.indicators.vwap;

import org.apache.commons.lang3.builder.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Konstantinos Antoniou
 */
public class Trade {

    private final BigDecimal tradePrice;
    private final BigDecimal tradeQty;
    private final Timestamp tradeTime;
    private final int tradeNo;

    /**
     * Constructor
     *
     * @param tradeLine      trade line coming as a string array from a csv file
     * @param tradesListSize the size of the trade list at the time of the trade creation
     */
    public Trade(String[] tradeLine, int tradesListSize) {
        tradeNo = tradesListSize + 1;
        tradeTime = Timestamp.valueOf(tradeLine[5].replace('"', ' ').trim());
        tradePrice = new BigDecimal(tradeLine[6]);
        tradeQty = new BigDecimal(tradeLine[7]);
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public BigDecimal getTradeQty() {
        return tradeQty;
    }

    public Timestamp getTradeTime() {
        return tradeTime;
    }

    public int getTradeNo() {
        return tradeNo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }

        final Trade trade = (Trade) o;

        return new EqualsBuilder()
                .append(getTradeNo(), trade.getTradeNo())
                .append(getTradePrice(), trade.getTradePrice())
                .append(getTradeQty(), trade.getTradeQty())
                .append(getTradeTime(), trade.getTradeTime())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getTradePrice())
                .append(getTradeQty())
                .append(getTradeTime())
                .append(getTradeNo())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tradePrice", tradePrice)
                .append("tradeQty", tradeQty)
                .append("tradeTime", tradeTime)
                .append("tradeNo", tradeNo)
                .toString();
    }
}