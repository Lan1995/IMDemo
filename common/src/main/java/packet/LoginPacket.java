package packet;

import codec.Command;
import lombok.Data;

@Data
public class LoginPacket extends Packet {

    private String userName;

    private String password;

    @Override
    public Command getCommand() {
        return null;
    }
}
