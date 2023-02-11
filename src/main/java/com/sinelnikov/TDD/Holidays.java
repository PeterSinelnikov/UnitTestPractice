package com.sinelnikov.TDD;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Holidays {

    private static List<LocalDate> holidays;

    public static void initHolidays(List<LocalDate> holidaysList) {
        holidays = holidaysList;
    }

    public static List<LocalDate> getHolidays() {
        return holidays == null ? Collections.emptyList() : holidays;
    }
}
