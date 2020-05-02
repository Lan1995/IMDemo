package channelHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelHandlerInit extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SpliterChannelHandler(Integer.MAX_VALUE, SpliterChannelHandler.MAX_SKIP, SpliterChannelHandler.DATA_LENGTH));
        pipeline.addLast(CodecChannelHandler.INSTANCE);
        pipeline.addLast(LoginChannelHandler.INSTANCE);
    }
}
