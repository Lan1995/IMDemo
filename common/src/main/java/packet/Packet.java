package packet;

import codec.Command;
import lombok.Data;

/**
 * Describe: 自定义协议顶层数据包
 * Date: 2020/5/2
 * Time: 下午4:41
 *
 * @author: m2keloop
 **/
@Data
public abstract class Packet {

    private byte version;

    public abstract Command getCommand();
}
