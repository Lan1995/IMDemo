package handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SplitChannelHandler());
        pipeline.addLast(CodecChannelHandler.INSTANCE);
        pipeline.addLast(LoginChannelHandler.INSTANCE);
        pipeline.addLast(new EncodeHandler());
    }
}
