package packet.response;

import codec.Command;
import lombok.Data;
import packet.Packet;

@Data
public class LoginResponsePacket extends Packet {

    private String uid;

    private String userName;

    private boolean loginSuccess;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REP;
    }
}
