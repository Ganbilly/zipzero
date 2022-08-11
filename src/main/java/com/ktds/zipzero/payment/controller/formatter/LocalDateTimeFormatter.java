package com.ktds.zipzero.payment.controller.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String pattern, Locale locale) throws ParseException {
        return LocalDateTime.parse(pattern, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
