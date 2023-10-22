package br.com.lucotavio.restspringboot.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataConverter {

    public static String localDateToString(LocalDate localDate, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String dateString = localDate.format(formatter);
        return dateString;
    }

    public static String localDateToString_dd_mm_yyyy_withSlash(LocalDate localDate){
        String dateString = localDateToString(localDate, "dd/MM/yyyy");
        return dateString;
    }

    public static LocalDate stringToLocalDate(String dateString, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }


    public static LocalDate string_dd_mm_yyyy_withSlashToLocalDate(String dateString){
        LocalDate localDate = stringToLocalDate(dateString, "dd/MM/yyyy");
        return localDate;
    }
}
