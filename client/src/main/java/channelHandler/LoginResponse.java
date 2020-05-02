package channelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import packet.LoginPacket;

@Slf4j
public class LoginResponse extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("------------- 当前连接活跃，可进行登录 --------------");
        LoginPacket packet = new LoginPacket();
        packet.setUserName("唐僧");
        packet.setPassword("123");
        ctx.writeAndFlush(packet);
    }
}
