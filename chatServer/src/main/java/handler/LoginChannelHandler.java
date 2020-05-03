package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import packet.LoginPacket;
import packet.response.LoginResponsePacket;
import session.SessionUtil;
import util.IDUtil;

@Slf4j
@ChannelHandler.Sharable
public class LoginChannelHandler extends SimpleChannelInboundHandler<LoginPacket> {

    public static LoginChannelHandler INSTANCE = new LoginChannelHandler();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket msg) throws Exception {
        //todo 魔术校验

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        String uid = IDUtil.getUID();
        responsePacket.setUid(uid);
        responsePacket.setUserName(msg.getUserName());
        if (msg.getPassword().equals("123")) {
            log.info("------------- {}登录成功 --------------", msg.getUserName());
            //存储连接
            Channel channel = ctx.channel();
            responsePacket.setLoginSuccess(true);
            responsePacket.setReason("");
            //todo　后续集中存储，保证水平扩展
            SessionUtil.bindSession(uid, channel);
        } else {
            responsePacket.setLoginSuccess(false);
            responsePacket.setReason("密码错误");
        }
        ctx.writeAndFlush(responsePacket);
        //ctx.channel().writeAndFlush(responsePacket);
    }

}
