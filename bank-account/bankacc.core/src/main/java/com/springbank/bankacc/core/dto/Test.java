package com.springbank.bankacc.core.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Test {

    public static void main(String[] args) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter();
        System.err.println(LocalDate.parse("27-SEP-2015", formatter));
        System.out.println(LocalDate.parse("26-Jan-2022", DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        //System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(LocalDateTime.parse("2021-06-18 10:24:12", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }
}
