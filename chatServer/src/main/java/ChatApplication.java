import channelHandler.ServerChannelHandlerInit;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Describe: IM服务器启动类
 * Date: 2020/5/2
 * Time: 下午6:02
 *
 * @author: m2keloop
 **/
@Slf4j
public class ChatApplication {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerChannelHandlerInit handlerInit = new ServerChannelHandlerInit();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(handlerInit);
        ChannelFuture future = bootstrap.bind("127.0.0.1", 8080);
        try {
            future.sync().addListener(listener -> {
                if (listener.isSuccess()) {
                    log.info("------------- 启动成功 --------------");
                } else {
                    log.info("------------- 启动失败 --------------");
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }
}
