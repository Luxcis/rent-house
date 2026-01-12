package top.luxcis.renthouse.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhuang
 */
@SuppressWarnings("unused")
@UtilityClass
public class PasswordUtil {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public static String encode(String raw) {
        return encoder.encode(raw);
    }

    public static boolean match(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}
