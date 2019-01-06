package project.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Class with some utils for randomness
 */
public class Utilities {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    /**
     *
     * @param to Limit to which int will be generated (from 0 to "to")
     * @return random int
     */
    public static int getRandomInt(int to) {
        return (int) Math.floor(Math.random() * (to + 1));
    }

    /**
     *
     * @param from Limit from which int will be generated (from "from" to "to")
     * @param to Limit to which int will be generated (from "from" to "to")
     * @return random int
     */
    public static int getRandomInt(int from, int to) {
        return from + (int) Math.floor(Math.random() * (to - from + 1));
    }


    /**
     *
     * @param from Limit from which float will be generated (from "from" to "to")
     * @param to Limit to which float will be generated (from "from" to "to")
     * @return random float
     */
    public static float getRandomFloat(float from, float to) {
        return (float) ((int) ((from + ((float) Math.random() * (to - from))) * 100)) / 100;
    }

    /**
     *
     * @param howManyDaysMin How many days from now as start of range
     * @param howManyDaysMax How many days from now as end of range
     * @return random date from range
     */
    public static String getRandomDate(int howManyDaysMin, int howManyDaysMax) {
        int randomDays = getRandomInt(howManyDaysMin, howManyDaysMax);
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(randomDays);
        return dateFormat.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
