package com.kap.indicators.vwap;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *  Trade.java
 *
 *  Purpose: Creates a trade object with the following fields:
 *           - Trade No.
 *           - Trade Time
 *           - Trade Price
 *           - Trade Quantity
 *
 *  @author Kostis
**/

public class Trade {

	private final BigDecimal tradePrice;
 	private final BigDecimal tradeQty;
 	private final Timestamp tradeTime;
	private final int tradeNo;

	public Trade (String[] tradeLine, int tradesListSize) {
		tradeNo = tradesListSize + 1;
		tradeTime = Timestamp.valueOf(tradeLine[5].replace('"', ' ').trim());
		tradePrice = new BigDecimal(tradeLine[6]);
		tradeQty = new BigDecimal(tradeLine[7]);
	}

	public Timestamp getTradeTime() {
		return tradeTime;
	}

	public BigDecimal getTradePrice(){
		return tradePrice;
	}

	public BigDecimal getTradeQty(){
		return tradeQty;
	}

	@Override
	public String toString() {
		return "Trade{" +
				"tradePrice=" + tradePrice +
				", tradeQty=" + tradeQty +
				", tradeTime=" + tradeTime +
				", tradeNo=" + tradeNo +
				'}';
	}
}