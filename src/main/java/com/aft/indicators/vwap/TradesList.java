package com.aft.indicators.vwap;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;
import java.io.*;

/**
 *
 * com.aft.indicators.vwap.TradesList.java
 *
 * Purpose: Takes a text file as an input. The file contains a full day's ORC order book data.
 *
 *          Indicative data format in the file:
 *
 *          "","newName","ref","inst","type","date","pz","qty","level"
 *          "1","36CH6",90,"6CH6",3,"2015-12-21 00:00:00.07500",0.7177,2,0
 *          "2","36CH6",90,"6CH6",3,"2015-12-21 00:00:00.07500",0.7177,3,0
 *          "3","36CH6",90,"6CH6",3,"2015-12-21 00:00:00.07500",0.7177,1,0
 *          "4","36CH6",90,"6CH6",3,"2015-12-21 00:00:00.07500",0.7177,19,0
 *
 *          Reads that file, identifies which transactions are trades, works together with the trade
 *          class to create these trades and then puts them in an ArrayList.
 *
 * Scope: Package local.
 *
 * @author Kostis
 *
**/

public class TradesList {

    private static final Logger LOGGER = Logger.getLogger( TradesList.class.getName() );

    public List <Trade> getTrades(String file) {
        long startTime = System.currentTimeMillis();

        List<Trade> tradesList = new ArrayList<>();

        String csvSplitBy = ",";
        String[] tradeLine;
        String dataLine;
        int eventType;

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            bufferedReader.readLine();

            while ((dataLine = bufferedReader.readLine()) != null) {
                tradeLine = dataLine.split(csvSplitBy);
                eventType = Integer.parseInt(tradeLine[4]);

                if (eventType == 3) {
                    tradesList.add(new Trade(tradeLine, tradesList.size()));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("getTrades:" + elapsedTime);
        return tradesList;
    }

}