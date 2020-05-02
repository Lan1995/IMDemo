package packet;

import codec.Command;
import lombok.Data;

/**
 * Describe: 心跳检测包
 * Date: 2020/5/2
 * Time: 下午4:44
 * @author: m2keloop
 **/
@Data
public class IdlePacket extends Packet {
    @Override
    public Command getCommand() {
        return null;
    }
}
