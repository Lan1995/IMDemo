package channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import packet.LoginPacket;
import session.SessionUtil;

import java.net.SocketAddress;

@Slf4j
@ChannelHandler.Sharable
public class LoginChannelHandler extends SimpleChannelInboundHandler<LoginPacket> {

    public static LoginChannelHandler INSTANCE = new LoginChannelHandler();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket msg) throws Exception {
        Channel channel = ctx.channel();
        SocketAddress socketAddress = channel.remoteAddress();
        log.info("------------- receive msg:{},address:{} --------------", msg, socketAddress);
        if (validLogin()) {
            log.info("------------- 登录成功 --------------");
            SessionUtil.addChannel(msg.getUserName(), channel);
            ctx.writeAndFlush("欢迎你:" + msg.getUserName());
        } else {
            log.info("------------- 登录失败 --------------");
            channel.close();
        }
    }

    protected boolean validLogin() {
        return true;
    }
}
