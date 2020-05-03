package command;

import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import packet.LoginPacket;

import java.util.Scanner;

public class LoginCommand implements Command {
    @Override
    public void exec(Channel channel) {
        System.out.println("请输入用户名:");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        while (StringUtils.isBlank(s)) {
            System.out.println("用户名不能为空,请重新输入");
            s = scanner.next();
        }
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setUserName("无锡");
        loginPacket.setPassword("123");
        channel.writeAndFlush(loginPacket);

    }
}
