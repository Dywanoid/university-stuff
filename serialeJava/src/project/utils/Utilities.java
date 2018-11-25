package project.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utilities {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static String hashFunction(String whatToHash) {
        try {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = whatToHash.getBytes(StandardCharsets.UTF_8);
        return whatToHash + " -> " +String.format("%064x", new BigInteger(1, messageDigest.digest(bytes)));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
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
