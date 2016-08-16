package utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Arjel on 7/16/2016.
 */

public class CyberTrackerUtilities {

    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date retrieveDate(Long date) {
        return new Date(date);
    }

    public static Boolean retrieveBool(Integer value) {
        if(value == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public static java.sql.Date convertToSqlDate(Date date) {
    	return new java.sql.Date(date.getTime());
    }
    
    public static Timestamp convertToTimestampDate(Date date) {
    	return new Timestamp(date.getTime());
    }

}
