/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author asimkaymak
 */
public class DateConverter {

    public static long dateToLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmddMMyyyy");
        LocalDateTime now = LocalDateTime.now();
        return Long.parseLong(dtf.format(now));
    }

    public static String longToStringDate(Object longDate) {
        String converted_date = String.valueOf(longDate);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime custom_date;
        String temp = "";
        for (int i = 0; i < 12 - converted_date.length(); i++) {
            temp += "0";
        }
        converted_date = temp + converted_date;

        custom_date = LocalDateTime.of(Integer.parseInt(converted_date.substring(8, 12)),
                Integer.parseInt(converted_date.substring(6, 8)),
                Integer.parseInt(converted_date.substring(4, 6)),
                Integer.parseInt(converted_date.substring(0, 2)),
                Integer.parseInt(converted_date.substring(2, 4)));

        return dtf.format(custom_date);
    }

}
