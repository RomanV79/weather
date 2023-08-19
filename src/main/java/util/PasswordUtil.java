package util;

import com.password4j.Password;

public class PasswordUtil {

    public static boolean isValidPassword (String password, String hash) {
        return Password.check(password, hash).withBcrypt();
    }

    public static String encryptPassword(String password) {
        return Password.hash(password).withBcrypt().getResult();
    }
}
