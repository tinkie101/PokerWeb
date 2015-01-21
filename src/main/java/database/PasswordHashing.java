package database;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Albert on 2015-01-20.
 */
public class PasswordHashing {

    public static String generateHash(String Password, String Salt)
    {
        String result = Password + Salt;
        String hashPas = Base64.encodeBase64String(result.getBytes());

        return hashPas;
    }

    public static String generateSalt()
    {
        SecureRandom random = new SecureRandom();
        String salt = new BigInteger(130, random).toString(32);

        return salt;
    }
}
