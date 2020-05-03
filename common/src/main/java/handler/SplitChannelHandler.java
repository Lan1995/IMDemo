package handler;

import com.sun.xml.internal.ws.util.StringUtils;
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
public class SplitChannelHandler extends LengthFieldBasedFrameDecoder {


    public static Integer MAX_SKIP = 7;
    public static Integer DATA_LENGTH = 4;

    public SplitChannelHandler() {

        super(Integer.MAX_VALUE, MAX_SKIP, DATA_LENGTH);
    }

    /**
     * 通过解码器来缓冲数据包
     *
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        log.info("------------- 粘拆包器处理中.... --------------");
        /*if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }*/

        return super.decode(ctx, in);
    }


    @Override
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        if (socketAddress != null) {
            log.info("------------- socket address:{} remove channel --------------", socketAddress.toString());
        }
    }
}
