package com.q.QJsonable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lsease on 5/6/15.
 */
public abstract class ApiDateHelpers {

    public static String API_DATE_FORMAT = "yyyy-MM-dd";
    public static String API_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static String API_TIME_FORMAT = "HH:mm:ss";
    public static String ZERO_TIME_STRING = "00:00:00";

    public static String dateToApiString(Date date)
    {
        DateFormat df = new SimpleDateFormat(API_DATE_FORMAT);
        return df.format(date);
    }

}
