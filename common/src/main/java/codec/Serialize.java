package codec;

public interface Serialize {

    byte[] serialize(Object o);

    Object deserialize(byte[] bytes);

    SerializeType getSerializeType();
}
