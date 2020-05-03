package command;

import io.netty.channel.Channel;

/**
 * Describe: 命令接口
 * Date: 2020/5/3
 * Time: 上午10:15
 *
 * @author: m2keloop
 **/
public interface Command {
    void exec(Channel channel);
}
