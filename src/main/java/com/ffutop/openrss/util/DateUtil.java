package com.ffutop.openrss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Li JinLing
 * @since 2023-12-22
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static DateFormat RFC822_DATETIME_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private static DateFormat RFC3339_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static final DateFormat RFC3339_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final Date INITIAL_DATE = new Date(0);

    public static String A_WEEK_AGO() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        return RFC3339_DATE_FORMAT.format(calendar.getTime());
    }

    public static String formatAsRFC3339Date(Date date) {
        return RFC3339_DATE_FORMAT.format(date);
    }

    public static Date parseFromRFC822Datetime(String datetime) {
        try {
            return RFC822_DATETIME_FORMAT.parse(datetime);
        } catch (ParseException px) {
            LOGGER.error("parse {} failed.", datetime, px);
            return INITIAL_DATE;
        }
    }
}
