package com.ef.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Customized reader for the command line options.
 */
public class CommandLineParser {
    private static Logger logger = LogManager.getLogger(CommandLineParser.class.getName());
    private static String daily_period = "daily";
    private static String hourly_period = "hourly";

    /**
     * Parse each individual option of the command line.
     * @param option
     * @return
     * @throws ParseException
     */
    private static Map<String, Object> parseOptions(String option) throws ParseException {
        final String argsWrong = "Invalid command line options";
        final String argsDurationWrong = "Invalid duration value (hourly/daily)";
        final String argsThresholdWrong = "Invalid threshold value (positive integer)";
        Map<String, Object> map = new HashMap<>();
        try {
            String[] pair = option.split("=");
            switch (pair[0]) {
                case "--startDate":
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
                    Date date = format1.parse(pair[1]);
                    map.put(pair[0].substring(2), date);
                    break;
                case "--duration":
                    if(daily_period.equals(pair[1]) || hourly_period.equals(pair[1])) {
                        map.put(pair[0].substring(2), pair[1]);
                    } else {
                        throw new ParseException(argsDurationWrong, 0);
                    }
                    break;
                case "--threshold":
                    int threshold = Integer.parseInt(pair[1]);
                    if(threshold < 1) {
                        throw new ParseException(argsThresholdWrong, 0);
                    }
                    map.put(pair[0].substring(2), threshold);
                    break;
                default:
                    throw new ParseException(argsWrong + " " + pair[0], 0);
            }
        } catch (ParseException | NumberFormatException e) {
            throw e;
        }
        return map;
    }

    /**
     * Parse the command line options and return a map with the settings.
     * @param args
     * @return
     * @throws ParseException
     */
    public static Map<String, Object> parseCommandLine(String[] args) throws ParseException {
        final String argsExpected = "Expected command line options (--startDate/--threshold/--duration)";
        final String argsException = "Exception parsing command line options";
        Map<String, Object> map = new HashMap<>();
        if(args.length < 3) {
            logger.info(argsExpected);
            throw new ParseException(argsExpected, 0);
        }
        Arrays.asList(args).stream().forEach(option -> {
            try {
                map.putAll(parseOptions(option));
            } catch (ParseException | NumberFormatException e) {
                logger.info(e);
                return;
            }
        });
        if(map.size() != 3) {
            logger.error(argsException);
            throw new ParseException(argsException, 0);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date)map.get("startDate"));
        String duration = (String)map.get("duration");
        if(hourly_period.equals(duration)) {
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        if(daily_period.equals(duration)) {
            cal.add(Calendar.DATE, 1);
        }
        Date end_date = cal.getTime();
        map.put("endDate", end_date);
        return map;
    }
}
