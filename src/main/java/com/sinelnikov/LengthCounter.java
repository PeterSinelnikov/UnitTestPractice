package com.sinelnikov;

import java.util.*;

public class LengthCounter {

    public Map<Integer, Set<String>> countWordsByLength(String input) {
        Map<Integer, Set<String>> resultMap = new HashMap<>();
        if (input == null || input.isEmpty()) {
            return resultMap;
        }
        String[] splitInput = input.toLowerCase().split("[^a-z-]+");
        for (String fragment : splitInput) {
            Integer length = fragment.length();
            Set<String> wordsOfLength = resultMap.getOrDefault(length, new HashSet<>());
            wordsOfLength.add(fragment);
            resultMap.put(length, wordsOfLength);
        }
        return resultMap;
    }
}