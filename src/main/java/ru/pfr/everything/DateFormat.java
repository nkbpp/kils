package ru.pfr.everything;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    //строку в дату
    public static Date stringToDate(String dat) {
        Date date = stringtodate_ddMMyyyy(dat);
        if (date == null) date = stringtodate_yyyyMMdd(dat);
        return date;
    }

    private static Date stringtodate_ddMMyyyy(String dat) {
        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return date = format.parse(dat);
        } catch (ParseException e) {
            //System.out.println("Не удалось распарсить дату stringtodate_ddMMyyyy("+dat+")");
        }
        return null;
    }

    private static Date stringtodate_yyyyMMdd(String dat) {
        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return date = format.parse(dat);
        } catch (ParseException e) {
            //System.out.println("Не удалось распарсить дату stringtodate_yyyyMMdd("+dat+")");
        }
        return null;
    }

    //дату в строку
    public static String datetostring_yyyyMMdd(Date dat) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat1.format(dat);
    }

    public static String datetostring_ddMMyyyy(Date dat) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat1.format(dat);
    }

    //строку в строку
    public static String DataEngToRus(String s){
        if(s == null || s.trim().equals("")){return "";}
        String s1 = s;
        try{
            s1 = datetostring_ddMMyyyy(stringToDate(s.trim()));
        }catch (Exception e){
            s1 = s;
            System.out.println("Не удалось распарсить дату DataEngToRus("+s+")");
        }
        return s1;
    }

    //текущая дата
    public static String tekDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    //текущая дата
    public static String tekDateRus(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String tekDateEng(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    //текущая дата
    public static String tekDatexml2(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        return dateFormat.format(date).replace("GMT","");
    }

    //текущий год
    public static int tekGod(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return Integer.parseInt(dateFormat.format(date));
    }

}
