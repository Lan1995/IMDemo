package codec;

import algorithm.Serialize;
import algorithm.SerializeType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Describe: 默认序列化方式
 * Date: 2020/5/2
 * Time: 下午4:53
 *
 * @author: m2keloop
 **/
public class FastJsonSerialize implements Serialize {

    public byte[] serialize(Object o) {
        return JSONObject.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) {
        return JSON.parseObject(bytes, clz);
    }

    @Override
    public byte getSerializeType() {
        return SerializeType.JSON;
    }
}
