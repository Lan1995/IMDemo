import command.LoginCommand;
import handler.ClientHandlerInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import session.SessionUtil;

import java.util.concurrent.TimeUnit;

/**
 * Describe: IM客户端
 * Date: 2020/5/2
 * Time: 下午6:10
 *
 * @author: m2keloop
 **/
@Slf4j
public class Client {

    public static final String HOST = "127.0.0.1";
    public static final Integer PORT = 8080;
    public static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        ClientHandlerInit handlerInit = new ClientHandlerInit();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(handlerInit);
        connect(bootstrap, HOST, PORT, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                ChannelFuture channelFuture = (ChannelFuture) future;
                Channel channel = channelFuture.channel();
                log.info("------------- 连接成功 --------------");
                handlerSuccess(channel);
            } else if (retry == 0) {
                log.info("------------- 连接失败 --------------");
            } else {
                int count = MAX_RETRY - retry;
                log.info("------------- 重试第{}次 --------------", count);
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), 1 << count, TimeUnit.SECONDS);
            }
        });
    }

    private static void handlerSuccess(Channel channel) {
        LoginCommand login = new LoginCommand();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    login.exec(channel);
                } else {
                    //11
                }
            }

        }).start();
    }
}
