package com.sinelnikov.TDD;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.time.DayOfWeek.*;
import static java.time.DayOfWeek.TUESDAY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopTest {

    private Shop shop;

    @BeforeAll
    public static void setup() {
        Holidays.initHolidays(Arrays.asList(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 7),
                LocalDate.of(2021, 3, 8),
                LocalDate.of(2021, 5, 3),
                LocalDate.of(2021, 5, 10),
                LocalDate.of(2021, 6, 21),
                LocalDate.of(2021, 6, 28),
                LocalDate.of(2021, 8, 24),
                LocalDate.of(2021, 10, 14),
                LocalDate.of(2021, 12, 27)
        ));
    }

    @ParameterizedTest(name = "{index} Should be open at {0}:{1}")
    @CsvSource(value = {"11:22", "8:00", "14:00", "12:30", "11:59", "19:59"}, delimiter = ':')
    void shouldBeRegularlyOpened(int hour, int minute) {
        //given
        WorkingHours workingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        shop = new Shop(workingHours, null, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 9, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
@ParameterizedTest(name = "{index} Should be closed at {0}:{1}")
    @CsvSource(value = {"7:0", "21:00", "12:15", "7:59", "20:01", "12:00", "12:29", "0:00"}, delimiter = ':')
    void shouldBeRegularlyClosed(int hour, int minute) {
        //given
        WorkingHours workingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        shop = new Shop(workingHours, null, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 9, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }
@Test
    @DisplayName("Should be opened on holiday")
    void shouldBeOpenedOnHoliday() {
        //given
        WorkingHours workingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        shop = new Shop(workingHours, null, true);
        LocalDateTime visitTime =LocalDateTime.of(2021, 5, 3, 11, 0);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
    @Test
    @DisplayName("Should be closed on holiday")
    void shouldBeClosedOnHoliday() {
        //given
        WorkingHours workingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        shop = new Shop(workingHours, null, false);
        LocalDateTime visitTime =LocalDateTime.of(2021, 5, 3, 11, 0);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }
    @ParameterizedTest(name = "{index} Should be open at {0}:{1} on exceptional day")
    @CsvSource(value = {"9:37", "8:00", "14:00", "12:30", "11:59", "14:59"}, delimiter = ':')
    void shouldBeOpenedOnExceptionDay(int hour, int minute) {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(15, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        ScheduleException exception = new ScheduleException(Collections.singletonMap(SATURDAY, exceptionalWorkingHours));
        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 10, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
    @ParameterizedTest(name = "{index} Should be open at {0}:{1} on exceptional day")
    @CsvSource(value = {"20:00", "21:59"}, delimiter = ':')
    void shouldBeOpenedOnNotFirstExceptionDay(int hour, int minute) {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(19, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(20, 0),
                LocalTime.of(22, 0)
        );
        Map<DayOfWeek, WorkingHours> exceptionalSchedule = new HashMap<>();
        exceptionalSchedule.put(TUESDAY, exceptionalWorkingHours);
        exceptionalSchedule.put(WEDNESDAY, exceptionalWorkingHours);
        ScheduleException exception = new ScheduleException(exceptionalSchedule);

        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 7, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
    @ParameterizedTest(name = "{index} Should be closed at {0}:{1} on exceptional day")
    @CsvSource(value = {"7:0", "21:00", "12:15", "7:59", "15:01", "12:00", "12:29", "0:00"}, delimiter = ':')
    void shouldBeClosedOnExceptionDay(int hour, int minute) {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(15, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        ScheduleException exception = new ScheduleException(Collections.singletonMap(SATURDAY, exceptionalWorkingHours));
        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 10, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }
    @ParameterizedTest(name = "{index} Should be open at {0}:{1} on exceptional day")
    @CsvSource(value = {"18:00", "19:59","22:00"}, delimiter = ':')
    void shouldBeClosedOnNotFirstExceptionDay(int hour, int minute) {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(19, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(20, 0),
                LocalTime.of(22, 0)
        );
        Map<DayOfWeek, WorkingHours> exceptionalSchedule = new HashMap<>();
        exceptionalSchedule.put(TUESDAY, exceptionalWorkingHours);
        exceptionalSchedule.put(WEDNESDAY, exceptionalWorkingHours);
        ScheduleException exception = new ScheduleException(exceptionalSchedule);

        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 7, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }
    @ParameterizedTest
    @CsvSource(value = {"11:22", "8:00", "12:01", "12:15", "12:29", "11:59", "19:59"}, delimiter = ':')
    void shouldBeOpenedWithoutBreak(int hour, int minute) {
        //given
        WorkingHours workingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0)
        );
        shop = new Shop(workingHours, null, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 9, 12, 15);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
    @ParameterizedTest
    @CsvSource(value = {"11:22", "8:00", "12:01", "12:15", "12:29", "11:59", "14:59"}, delimiter = ':')
    void shouldBeOpenedWithoutBreakOnExceptionDay(int hour, int minute) {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(15, 0)
        );
        ScheduleException exception = new ScheduleException(Collections.singletonMap(SATURDAY, exceptionalWorkingHours));
        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 10, hour, minute);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertTrue(result);
    }
    @Test
    @DisplayName("Should be closed if there is a non operational day")
    void shouldBeClosedIfExceptionWorkingDayIsNull() {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        ScheduleException exception = new ScheduleException(Collections.singletonMap(WEDNESDAY, null));
        shop = new Shop(regularWorkingHours, exception, true);
        LocalDateTime visitTime = LocalDateTime.of(2021, 4, 7, 11, 30);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }
    @Test
    @DisplayName("Should be closed if it is holiday and exceptional day")
    void shouldBeClosedIfIsHolidayAndExceptionalDay() {
        //given
        WorkingHours regularWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30)
        );
        WorkingHours exceptionalWorkingHours = new WorkingHours(
                LocalTime.of(8, 0),
                LocalTime.of(15, 0)
        );
        ScheduleException exception = new ScheduleException(Collections.singletonMap(WEDNESDAY, exceptionalWorkingHours));
        shop = new Shop(regularWorkingHours, exception, false);
        LocalDateTime visitTime = LocalDateTime.of(2021, 5, 3, 11, 0);
        //when
        boolean result = shop.doShopping(visitTime);
        //then
        assertFalse(result);
    }



}
