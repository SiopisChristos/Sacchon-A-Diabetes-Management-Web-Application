package com.pfizer.sacchon.repository.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DateConverter {

    public static List<Date> startAndEndOfDate(Date date){
        List<Date> dates = new ArrayList<>();

        //Convert Date to LocalDate
        LocalDate dateTimeStart = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        //get the Start of the Date
        dateTimeStart.atStartOfDay();
        dates.add(java.sql.Date.valueOf(dateTimeStart));

        //get the End of the Date
        LocalDateTime endOfDay = dateTimeStart.atTime(23,59,59);
        dates.add(java.sql.Timestamp.valueOf(endOfDay));

        return dates;
    }

}
