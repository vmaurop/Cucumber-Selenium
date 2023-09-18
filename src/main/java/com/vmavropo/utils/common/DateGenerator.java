package com.vmavropo.utils.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateGenerator {

    private DateGenerator() {
        throw new IllegalStateException("DateGenerator class");
    }

    public static final String DATE_NOW_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT_PAY_PLANS = "yyyy-MM-dd";


    public static String convertDateTime(String dateToConvert, String currentFormat, String expectedFormat) {
        DateTimeFormatter currentPattern = DateTimeFormatter.ofPattern(currentFormat);
        DateTimeFormatter expectedPattern = DateTimeFormatter.ofPattern(expectedFormat);
        LocalDateTime currentDate = LocalDateTime.parse(dateToConvert, currentPattern);
        return currentDate.format(expectedPattern);
    }

    public static String convertDate(String dateToConvert, String currentFormat, String expectedFormat) {
        DateTimeFormatter currentPattern = DateTimeFormatter.ofPattern(currentFormat);
        DateTimeFormatter expectedPattern = DateTimeFormatter.ofPattern(expectedFormat);
        LocalDate currentDate = LocalDate.parse(dateToConvert, currentPattern);
        return currentDate.format(expectedPattern);
    }
}
