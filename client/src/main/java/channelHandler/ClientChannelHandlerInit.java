package channelHandler;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Describe: 客户端Channel handle 连接器
 * Date: 2020/5/2
 * Time: 下午6:12
 *
 * @author: m2keloop
 **/
public class ClientChannelHandlerInit extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new SpliterChannelHandler(Integer.MAX_VALUE, SpliterChannelHandler.MAX_SKIP, SpliterChannelHandler.DATA_LENGTH));
        pipeline.addLast(CodecChannelHandler.INSTANCE);
        pipeline.addLast(new LoginResponse());
    }
}
