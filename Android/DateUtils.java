package com.q.QJsonable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lsease on 5/6/15.
 */
public abstract class DateUtils {

    public static String DISPLAY_TIME_FORMAT = "h:mm a";
    public static String DISPLAY_DATE_FORMAT = "M/dd/yyyy";

    public static String monthIndexAsString(Integer index, Integer offset)
    {
        if(index == null)
        {
            return "";
        }

        index = index - offset + 1;

        String result = "";

        SimpleDateFormat numberFormat = new SimpleDateFormat("MM");
        SimpleDateFormat stringFormat  = new SimpleDateFormat("MMMM", Locale.US);

        try {
            Date date = numberFormat.parse(index.toString());
            result = stringFormat.format(date);
        }
        catch (Exception e){}


        return result;
    }

    public static Date addDaysToDate(Date date, int daysToIncrement)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime ( date ); // convert your date to Calendar object
        cal.add(Calendar.DATE, daysToIncrement);
        return cal.getTime();
    }

    public static Date addHoursToDate(Date date, int hoursToIncrement)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime ( date ); // convert your date to Calendar object
        cal.add(Calendar.HOUR, hoursToIncrement);
        return cal.getTime();
    }

    public static Date dateWithoutTime(Date date)
    {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return outputFormatter.parse(outputFormatter.format(date));
        }
        catch (Exception e)
        {
            return date;
        }
    }
}
