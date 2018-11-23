package project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class Utilities {
    public static String hashFunction(String whatToHash) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = whatToHash.getBytes(StandardCharsets.UTF_8);
        return whatToHash + " -> " +String.format("%064x", new BigInteger(1, messageDigest.digest(bytes)));
    }
}
