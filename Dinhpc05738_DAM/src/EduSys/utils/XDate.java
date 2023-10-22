/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EduSys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class XDate {

    static SimpleDateFormat formatter = new SimpleDateFormat();

    public static Date toDate(String date, String pattern) {
        try {
            formatter.applyPattern(pattern);
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date, String pattern) {
        formatter.applyPattern(pattern);
        return formatter.format(date);
    }

    public static Date now() {
        return new Date();
    }

    public static Date addDay(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
