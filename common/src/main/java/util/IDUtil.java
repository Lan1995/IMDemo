package util;

import java.util.UUID;

public class IDUtil {

    public static String getUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
