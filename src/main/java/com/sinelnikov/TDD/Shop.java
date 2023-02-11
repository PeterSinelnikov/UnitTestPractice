package com.sinelnikov.TDD;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shop {

    private WorkingHours workingHours;
    ScheduleException exceptional;
    boolean workOnHolidays;

    public Shop(WorkingHours workingHours, ScheduleException exception, boolean workOnHolidays) {
        this.workingHours = workingHours;
        this.exceptional = exception;
        this.workOnHolidays = workOnHolidays;
    }

    public boolean doShopping(LocalDateTime shoppingTime) {
        if (exceptional != null) {
            DayOfWeek shoppingDayOfWeek = shoppingTime.toLocalDate().getDayOfWeek();
            if (exceptional.getExceptionalSchedule().containsKey(shoppingDayOfWeek)) {
                workingHours = exceptional.getExceptionalSchedule().get(shoppingDayOfWeek);
            }
        }
        if (workingHours == null) {
            return false;
        }
        if (!workOnHolidays) {
            if (Holidays.getHolidays().contains(shoppingTime.toLocalDate())) {
                return false;
            }
        }
        LocalTime currentTime = shoppingTime.toLocalTime();
        if (currentTime.isBefore(workingHours.getOpenFrom())
                || currentTime.isAfter(workingHours.getOpenUntil())
                || currentTime.equals(workingHours.getOpenUntil())) {
            return false;
        }
        if (workingHours.getBreakStart() != null && workingHours.getBreakEnd() != null) {
            if (currentTime.equals(workingHours.getBreakStart())
                    || currentTime.isAfter(workingHours.getBreakStart())
                    && currentTime.isBefore(workingHours.getBreakEnd())) {
                return false;
            }
        }
        return true;
    }
}
