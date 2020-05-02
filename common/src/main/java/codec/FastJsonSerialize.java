package codec;

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
    public Object deserialize(byte[] bytes) {
        return JSON.parseObject(new String(bytes));
    }

    @Override
    public SerializeType getSerializeType() {
        return SerializeType.FastJSON;
    }
}
