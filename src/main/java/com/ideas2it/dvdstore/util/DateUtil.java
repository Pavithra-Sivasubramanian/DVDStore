package com.ideas2it.dvdstore.util;

import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import com.ideas2it.dvdstore.common.Constants;

/**
 * <p>
 * This helper class is used perform operations regarding Date.
 * </p>
 *
 * @author  Pavithra S 
 */
public class DateUtil {

    /**
     * Creation of this constructor is to prevent instantiation from 
     * any other class.
     */
    private DateUtil() {
    }

    /** 
     * <p>
     * Used to find the differnce between the date from which the DVD was
     * released and the present date. 
     * </p>
     *
     * @param  date  The Release Date starting from which the time period is to 
     *               be calculated
     * @return Returns the timeperiod between Release Date and the Current Date
     */
    public static String dateDifference(Date date) {
        StringBuilder timePeriod = new StringBuilder();

        if (null != date) {
            LocalDate localDate = date.toInstant()
                       .atZone(ZoneId.systemDefault())
                       .toLocalDate();
            LocalDate today = LocalDate.now();

            Period time = Period.between(localDate, today);
      
            Integer years = time.getYears();
            Integer months = time.getMonths();
            Integer days = time.getDays();
            if (1 <= years) {
                timePeriod.append(years).append(Constants.MESSAGE_YEARS);
                return timePeriod.toString(); 
            } else if (1 > years && 1 <= months) {
                timePeriod.append(months).append(Constants.MESSAGE_MONTHS)
                    .append(days).append(Constants.MESSAGE_DAYS);
                return timePeriod.toString();
            } else if (1 > months && 1 > years) {
                timePeriod.append(days).append(Constants.MESSAGE_DAYS);
                return timePeriod.toString();
            }
        } 
        return null;
    }
    
    /**
     * <p>
     * Checks whether the Date obtained from the user is yet to come
     * </p>
     *
     * @return Returns true, if it is Future date. Otherwise, returns false
     */
    public static Boolean isFutureDate(LocalDate localDate) {
        LocalDate today = LocalDate.now();           
        return (localDate.isAfter(today));
    }            
}
