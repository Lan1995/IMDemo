package algorithm;

public interface Serialize {

    byte[] serialize(Object o);

    <T> T deserialize(byte[] bytes,Class<T> clz);

    byte getSerializeType();
}
