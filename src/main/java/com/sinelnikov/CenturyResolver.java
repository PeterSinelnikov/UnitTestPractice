package com.sinelnikov;

public class CenturyResolver {

    public int ResolveCentury(int year) {

        if (year == 0) {
           throw new IllegalArgumentException("there was no 0 year.");
        }
        if (year % 100 == 0) {
            return year / 100;
        } else {
            if (year > 0) {
               return (year / 100 ) + 1;
            } else {
                return (year / 100 ) - 1;
            }
        }
    }
}
