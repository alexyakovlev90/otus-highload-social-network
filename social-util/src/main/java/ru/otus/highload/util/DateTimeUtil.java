package ru.otus.highload.util;

import java.time.*;
import java.util.TimeZone;

public class DateTimeUtil {

    /**
     * @param strDateTime - ISO_INSTANT 2016-08-16T15:23:01Z
     *                    get date time only
     */
    public static LocalDateTime parseDateTime(String strDateTime) {
        Instant instant = Instant.parse(strDateTime);
        return LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
    }

    /**
     * @param strDate ISO_LOCAL_DATE 2016-08-16
     */
    public static LocalDate parseIsoDate(String strDate) {
        return LocalDate.parse(strDate);
    }


    /**
     * 1499070300L -> 2017-07-03T10:25
     */
    public static LocalDateTime fromSeconds(long timestampSec) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampSec),
                TimeZone.getDefault().toZoneId());
    }

    /**
     * 1499070300000L -> 2017-07-03T10:25
     */
    public static LocalDateTime fromMilliseconds(long timestampMilliSec) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMilliSec),
                TimeZone.getDefault().toZoneId());
    }

}
