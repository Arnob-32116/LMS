package com.example.loginpagemain;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateandTime {
    public static void LocalDateTimeApi()
    {

        // the current date
        LocalDate date = LocalDate.now();
        System.out.println("the current date is "+
                date);


        // the current time
        LocalTime time = LocalTime.now();
        System.out.println("the current time is "+
                time);


        // will give us the current time and date
        LocalDateTime current = LocalDateTime.now();
        System.out.println("current date and time : "+
                current);


        // to print in a particular format
        DateTimeFormatter format =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formatedDateTime = current.format(format);

        System.out.println("in formatted manner "+
                formatedDateTime);


        // printing months days and seconds
        Month month = current.getMonth();
        int day = current.getDayOfMonth();
        int seconds = current.getSecond();
        System.out.println("Month : "+month+" day : "+
                day+" seconds : "+seconds);

        // printing some specified date
        LocalDate date2 = LocalDate.of(1950,1,26);
        System.out.println("the republic day :"+date2);

        // printing date with current time.
        LocalDateTime specificDate =
                current.withDayOfMonth(24).withYear(2016);

        System.out.println("specific date with "+
                "current time : "+specificDate);
    }

    public static void checkingPeriod()
    {
        LocalDate date1 = LocalDate.now();

        LocalDate date2 =
                LocalDate.of(2014, Month.DECEMBER, 12);

        Period gap = Period.between(date2, date1);
        System.out.println("gap between dates "+
                "is a period of "+gap);
    }

    // Driver code
    public static void main(String[] args)
    {
        //LocalDateTimeApi();
        checkingPeriod();
    }
}
