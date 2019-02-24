package com.kap.indicators.vwap;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Konstantinos Antoniou
 */
public class TimeIntervalsList {

    private static final Logger LOGGER = Logger.getLogger(TimeIntervalsList.class);

    private static final String START_STRING = "Please provide your preferred VWAP time intervals in the form of non zero positive integer.";
    private static final String STOP_STRING = "The definition of VWAP time intervals has now finished.";
    private static final String ERROR_MESSAGE = "The number you provided is wrong. Please provide a non zero positive integer.";
    private static final String STOP_TOKEN = "stop";

    private final List<Integer> timeIntervals = new ArrayList<>();
    private final Scanner timeIntervalsScanner = new Scanner(System.in);

    /**
     * Gets VWAP time intervals as an input from the user in the form of non zero positive integers
     * and stores them in an ArrayList.
     *
     * @return an ArrayList with the requested VWAP time intervals
     */
    public List<Integer> getTimeIntervals() {
        LOGGER.info(TimeIntervalsList.START_STRING);

        String input;
        while (timeIntervalsScanner.hasNextLine()) {
            input = timeIntervalsScanner.nextLine();
            if (TimeIntervalsList.STOP_TOKEN.equals(input)) {
                LOGGER.info(TimeIntervalsList.STOP_STRING);
                break;
            } else {
                checkTimeInterval(input);
            }
        }
        timeIntervalsScanner.close();
        return timeIntervals;
    }

    /**
     * This method parses the time interval and if its correct
     * adds it to the list.
     *
     * @param input Input string from command line
     */
    private void checkTimeInterval(String input) {
        try {
            int timeInterval = Integer.parseInt(input);
            if (timeInterval <= 0) {
                throw new NumberFormatException();
            } else {
                timeIntervals.add(timeInterval);
            }
        } catch (NumberFormatException e) {
            LOGGER.error(TimeIntervalsList.ERROR_MESSAGE);
        }
    }
}