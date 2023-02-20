package com.sinelnikov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LengthCounterTest {
    LengthCounter lengthCounter;

    @BeforeEach
    void setUp() {
        lengthCounter = new LengthCounter();
    }

    @ParameterizedTest(name = "Should return new empty map when passed null or empty input")
    @NullAndEmptySource
    public void shouldReturnEmptyMapWhenEmptyInput(String input) {
        Map<Integer, Set<String>> actual = lengthCounter.countWordsByLength(input);
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @ParameterizedTest(name = "{index} Should return map with {1} number of entries")
    @CsvSource(value = {"A!,1", "A:AA,2", "A: BC? DEF, 3"})
    public void shouldReturnMapWithExpectedNumberOfEntries(String input, int occurrences) {
        int actual = lengthCounter.countWordsByLength(input).size();
        assertEquals(actual, occurrences);
    }

    @ParameterizedTest(name = "{index} Should put \"{0}\" to set of length {1}")
    @CsvSource(value = {"a, 1", "bc, 2", "def, 3"})
    public void shouldPutFragmentsToExpectedSet(String expectedFragment, int length) {
        Map<Integer, Set<String>> map = lengthCounter.countWordsByLength("A bc def");
        assertTrue(map.get(length).contains(expectedFragment));
    }

    @Test
    @DisplayName("Should ignore special characters when splitting")
    public void shouldIgnoreSpecialCharacters() {
        Map<Integer, Set<String>> map = lengthCounter.countWordsByLength("!@#$%^&*()=_+{}[];:'./ \\ \"~`|");
        assertEquals(0, map.size());
    }

}