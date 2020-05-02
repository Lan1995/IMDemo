package channelHandler;

import codec.PocketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import packet.Packet;

import java.util.List;

/**
 * Describe: 编解码器
 * Date: 2020/5/2
 * Time: 下午5:29
 *
 * @author: m2keloop
 **/
@ChannelHandler.Sharable
public class CodecChannelHandler extends MessageToMessageCodec<ByteBuf, Packet> {

    public static CodecChannelHandler INSTANCE = new CodecChannelHandler();

    private CodecChannelHandler() {
    }

    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        out.add(PocketCodec.encode(ctx.alloc().buffer(), msg));
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(PocketCodec.decode(msg));
    }
}
