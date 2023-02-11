package com.sinelnikov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CenturyResolverTest {
    CenturyResolver testee;
    @BeforeEach
    void setUp() {
        testee = new CenturyResolver();
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when passed 0 year")
    public void shouldThrowIAEWhenPassedZero() {
        assertThrows(IllegalArgumentException.class, () -> testee.ResolveCentury(0));
    }

    @ParameterizedTest(name = "{index} Should return {1} century for {0} year")
    @CsvSource(value = {"99,1", "100,1", "101,2", "-99,-1", "-100,-1", "-101,-2"})
    public void shouldReturnExpectedValue(int year, int expectedCentury) {
        int century = testee.ResolveCentury(year);
        assertEquals(expectedCentury,century);
    }
}
