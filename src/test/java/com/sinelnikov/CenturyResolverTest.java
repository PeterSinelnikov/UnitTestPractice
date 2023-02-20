package com.sinelnikov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CenturyResolverTest {
    CenturyResolver centuryResolver;
    @BeforeEach
    void setUp() {
        centuryResolver = new CenturyResolver();
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when passed 0 year")
    public void shouldThrowIAEWhenPassedZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> centuryResolver.ResolveCentury(0));
        assertEquals("there was no 0 year.", exception.getMessage());
    }

    @ParameterizedTest(name = "{index} Should return {1} century for {0} year")
    @CsvSource(value = {"99,1", "100,1", "101,2", "-99,-1", "-100,-1", "-101,-2"})
    public void shouldReturnExpectedValue(int year, int expectedCentury) {
        int century = centuryResolver.ResolveCentury(year);
        assertEquals(expectedCentury,century);
    }
}
