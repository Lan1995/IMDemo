package codec;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import packet.Packet;

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

    public static boolean validMagicNum(int readInt) {
        return magicNum.equals(readInt);
    }

    //4 byte magicnum + 1 byte version + 1 byte Algorithm + 1 byte command + 4 byte length + data
    public static ByteBuf encode(ByteBuf buf, Packet packet) {
        buf.writeInt(magicNum);
        buf.writeByte(packet.getVersion());
        //todo 后续改造成SPI方式
        Serialize algorithm = AlgorithmFactory.getDefaultAlgorithm();
        //buf.writeByte()
        //buf.writeByte(packet.getCommand());
        byte[] data = algorithm.serialize(packet);
        buf.writeByte(data.length);
        buf.writeBytes(data);
        return buf;
    }

    public static Object decode(ByteBuf buf) {
        print(buf);
        //todo 后续抽离
        buf.skipBytes(5);
        print(buf);
        int dataSize = buf.readInt();
        print(buf);
        byte[] bytes = new byte[dataSize];
        buf.readBytes(bytes);
        print(buf);
        return AlgorithmFactory.getDefaultAlgorithm().deserialize(bytes);
    }

    public static void print(ByteBuf buf) {
        log.info("------------- readIndex:{} --------------", buf.readerIndex());
        log.info("------------- writerIndex:{} --------------", buf.writerIndex());
        log.info("------------- read size:{} --------------", buf.readableBytes());
        log.info("------------- writer size:{} --------------", buf.writableBytes());
    }
}
