package  com.abc.xyz.common;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

    private static Map<Integer, String> msg = new HashMap<>();

    public static String getMessage(int code) {
        return msg.get(code);
    }

    public static void addMessage(int code, String err) {
        msg.put(code, err);
    }
}