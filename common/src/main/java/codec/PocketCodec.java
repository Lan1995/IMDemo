package codec;

import algorithm.AlgorithmFactory;
import algorithm.Serialize;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import packet.LoginPacket;
import packet.Packet;
import packet.response.LoginResponsePacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Describe: 编解码工具类
 * Date: 2020/5/2
 * Time: 下午7:51
 *
 * @author: m2keloop
 **/
@Slf4j
public class PocketCodec {

    public static final Integer magicNum = 0x12345678;

    private static Map<Byte, Class<? extends Packet>> packetMap = new ConcurrentHashMap<>();

    static {
        packetMap.put(Command.LOGIN, LoginPacket.class);
        packetMap.put(Command.LOGIN_REP, LoginResponsePacket.class);
    }

    public static boolean validMagicNum(int readInt) {
        return magicNum.equals(readInt);
    }

    //4 byte magicnum + 1 byte version + 1 byte Algorithm + 1 byte command + 4 byte length + data
    public static ByteBuf encode(ByteBuf buf, Packet packet) {
        //魔数
        buf.writeInt(magicNum);
        //版本
        buf.writeByte(packet.getVersion());
        //todo 后续改造成SPI方式
        Serialize algorithm = AlgorithmFactory.getDefaultAlgorithm();
        //序列化算法类型
        buf.writeByte(algorithm.getSerializeType());
        //当前指令类型
        buf.writeByte(packet.getCommand());
        //数据长度
        byte[] data = algorithm.serialize(packet);
        buf.writeInt(data.length);
        //数据
        buf.writeBytes(data);
        return buf;
    }

    public static Packet decode(ByteBuf buf) {
        print(buf);
        //跳过魔数
        buf.skipBytes(4);
        //跳过版本
        buf.skipBytes(1);
        //跳过算法类型
        buf.skipBytes(1);
        //指令类型
        byte command = buf.readByte();
        //数据长度
        int dataSize = buf.readInt();
        byte[] bytes = new byte[dataSize];
        buf.readBytes(bytes);
        Class<? extends Packet> clz = packetMap.get(command);
        return AlgorithmFactory.getDefaultAlgorithm().deserialize(bytes, clz);
    }

    public static void print(ByteBuf buf) {
        log.info("------------- readIndex:{} --------------", buf.readerIndex());
        log.info("------------- writerIndex:{} --------------", buf.writerIndex());
        log.info("------------- read size:{} --------------", buf.readableBytes());
        log.info("------------- writer size:{} --------------", buf.writableBytes());
    }
}
