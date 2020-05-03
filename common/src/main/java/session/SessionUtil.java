package session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    /**
     * uid:channel
     */
    private static Map<String, Channel> sessionMap = new ConcurrentHashMap<>();


    public static void bindSession(String uid, Channel channel) {
        sessionMap.putIfAbsent(uid, channel);
    }

    public static void removeSession(String uid) {
        sessionMap.remove(uid);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(SessionKey.session);
    }
}
