package com.sinelnikov.TDD;

import java.time.LocalTime;

public class WorkingHours {

    private final LocalTime openFrom;
    private final LocalTime openUntil;
    private LocalTime breakStart;
    private LocalTime breakEnd;


    public WorkingHours(LocalTime openFrom, LocalTime openUntil, LocalTime breakStart, LocalTime breakEnd) {
        this.openFrom = openFrom;
        this.openUntil = openUntil;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
    }

    public WorkingHours(LocalTime openFrom, LocalTime openUntil) {
        this.openFrom = openFrom;
        this.openUntil = openUntil;
    }

    public LocalTime getOpenFrom() {
        return openFrom;
    }

    public LocalTime getOpenUntil() {
        return openUntil;
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }
}
