package session;

import io.netty.util.AttributeKey;

public interface SessionKey {

    AttributeKey<MySession> session = AttributeKey.newInstance("session");
}
