package ru.dvkombarov.app.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private void DateUtils() {
    }

    /**
     * Возвращает дату в виде строки в формате yyyy-MM-dd
     *
     * @param date дата
     * @return строковое представление даты
     */
    public static String getStringFromDate(Date date) {
        return formatter.format(date);
    }

    /**
     * Возвращает дату из строки в формате yyyy-MM-dd
     *
     * @param string строка в формате yyyy-MM-dd
     * @return дата
     */
    public static Date getDateFromString(String string) throws ParseException {
        return StringUtils.isEmpty(string) ? null : formatter.parse(string);
    }
}
