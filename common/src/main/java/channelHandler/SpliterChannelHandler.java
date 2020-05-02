package channelHandler;

import codec.PocketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;

/**
 * Describe: 粘包拆包处理
 * Date: 2020/5/2
 * Time: 下午5:00
 *
 * @author: m2keloop
 **/
@Slf4j
public class SpliterChannelHandler extends LengthFieldBasedFrameDecoder {


    public static Integer MAX_SKIP = 5;
    public static Integer DATA_LENGTH = 4;

    public SpliterChannelHandler(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {

        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //检查魔术版本是否正确
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        ByteBuf buf = (ByteBuf) msg;
        if (PocketCodec.validMagicNum(buf.readInt())) {
            super.channelRead(ctx, msg);
        } else {
            //直接关闭连接
            log.info("------------- socket address:{} check magic Error, close channel --------------", socketAddress.toString());
            ctx.channel().close();
        }
    }

    @Override
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.info("------------- socket address:{} remove channel --------------", socketAddress.toString());
    }
}
