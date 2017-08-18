package com.david;

import org.apache.commons.lang.time.DateUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by david on 18/08/2017.
 */
public class Utils {
    public static int date2intSecond(Date date) {
        return (int) (date.getTime() / 1000);
    }
    public static Date intSecond2Date(int second) {
        return new Date(second * 1000L);
    }
    public static int string2int(String time) {
        try {
            return date2intSecond(DateUtils.parseDate(time, new String[]{"yyyy-MM-dd HH:mm:ss"}));
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    public static String int2String(int time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(intSecond2Date(time));
    }

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
