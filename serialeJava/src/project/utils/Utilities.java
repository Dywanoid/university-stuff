package project.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Utilities {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public static int getRandomInt(int to) {
        return (int) Math.floor(Math.random() * (to + 1));
    }

    public static int getRandomInt(int from, int to) {
        return from + (int) Math.floor(Math.random() * (to - from + 1));
    }


    public static float getRandomFloat(float from, float to) {
        return (float) ((int) ((from + ((float) Math.random() * (to - from))) * 100)) / 100;
    }

    public static String getRandomDate(int howManyDaysMin, int howManyDaysMax) {
        int randomDays = getRandomInt(howManyDaysMin, howManyDaysMax);
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(randomDays);
        return dateFormat.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
