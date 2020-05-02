package session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    /**
     * uid:channel
     */
    private static Map<String, Channel> sessionMap = new ConcurrentHashMap<>();


    public static void addChannel(String uid, Channel channel) {
        sessionMap.putIfAbsent(uid, channel);
    }

    public static void remove(String uid) {
        sessionMap.remove(uid);
    }
}
