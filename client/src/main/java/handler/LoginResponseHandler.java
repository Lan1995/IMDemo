package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import packet.response.LoginResponsePacket;
import session.MySession;
import session.SessionKey;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("------------- 连接关闭 --------------");
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        MySession session = new MySession();
        session.setUid(msg.getUid());
        session.setUserName(msg.getUserName());
        if (msg.isLoginSuccess()) {
            //channel 标记登录成功
            log.info("------------- 欢迎{},登录成功 --------------", msg.getUserName());
            Channel channel = ctx.channel();
            channel.attr(SessionKey.session).set(session);
        } else {
            log.info("------------- 登录失败，原因为:{} --------------", msg.getReason());
        }
    }
}
