package com.sinelnikov.TDD;

import java.time.DayOfWeek;
import java.util.Map;

public class ScheduleException {
    private final Map<DayOfWeek,WorkingHours> exceptionalSchedule;

    public ScheduleException(Map<DayOfWeek,WorkingHours> exceptionalSchedule) {
        this.exceptionalSchedule = exceptionalSchedule;
    }

    public Map<DayOfWeek, WorkingHours> getExceptionalSchedule() {
        return exceptionalSchedule;
    }

}
