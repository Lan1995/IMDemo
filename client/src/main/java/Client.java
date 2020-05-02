import channelHandler.ClientChannelHandlerInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import packet.LoginPacket;

import java.util.Scanner;

/**
 * Describe: IM客户端
 * Date: 2020/5/2
 * Time: 下午6:10
 *
 * @author: m2keloop
 **/
@Slf4j
public class Client {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        ClientChannelHandlerInit handlerInit = new ClientChannelHandlerInit();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(handlerInit);
        connect(bootstrap, 8080);

    }

    public static void connect(Bootstrap bootstrap, Integer port) {
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            future.addListener(listener -> {
                if (listener.isSuccess()) {
                    log.info("------------- 连接成功,端口号:{} --------------", port);
                    Channel channel = future.channel();
                    LoginPacket packet = new LoginPacket();
                    packet.setUserName("唐僧");
                    packet.setPassword("123");
                    channel.writeAndFlush(packet);
                    new Thread(() -> {
                        while (true) {

                        }
                    });

                } else {
                    log.info("------------- 连接失败，重连中.... --------------");
                    connect(bootstrap, port + 1);
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
