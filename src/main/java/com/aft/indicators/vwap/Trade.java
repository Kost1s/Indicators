package com.aft.indicators.vwap;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *  com.aft.indicators.vwap.Trade.java
 *
 *  Purpose: Creates a trade object with the following fields:
 *           - com.aft.indicators.vwap.Trade No.
 *           - com.aft.indicators.vwap.Trade Time
 *           - com.aft.indicators.vwap.Trade Price
 *           - com.aft.indicators.vwap.Trade Quantity
 *
 *  @author Kostis
**/

public class Trade {
	
	private final BigDecimal tradePrice;
 	private final BigDecimal tradeQty;
 	private final Timestamp tradeTime;
	private final int tradeNo;

	private String trimmedTradeTime;
	private String valueTradeTime;

	public Trade (String[] tradeLine, int tradesListSize) {
		valueTradeTime = tradeLine[5];
		valueTradeTime = valueTradeTime.replace('"', ' ');
		trimmedTradeTime = valueTradeTime.trim();

		this.tradeNo = tradesListSize + 1;
		this.tradeTime = Timestamp.valueOf(trimmedTradeTime);
		this.tradePrice = new BigDecimal(tradeLine[6]);
		this.tradeQty = new BigDecimal(tradeLine[7]);
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
		return "com.aft.indicators.vwap.Trade{" +
				"tradePrice=" + tradePrice +
				", tradeQty=" + tradeQty +
				", tradeTime=" + tradeTime +
				", tradeNo=" + tradeNo +
				'}';
	}
}