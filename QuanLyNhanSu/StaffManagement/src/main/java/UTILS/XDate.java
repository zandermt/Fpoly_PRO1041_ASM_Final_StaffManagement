/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILS;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class XDate {
    static SimpleDateFormat form= new SimpleDateFormat();
    public static Date toDate(String date, String pattern){
        try{
            form.applyPattern(pattern);
            return form.parse(date);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static String toString(Date date,String pattern){
        form.applyPattern(pattern);
        return form.format(date);
    }
    public static Date addDays(Date date,long days){
        date.setTime(date.getTime()+days*24*60*60*1000);
        return date;
    }
}
