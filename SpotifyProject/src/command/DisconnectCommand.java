package command;

import static communication.ResponseConstants.DISCONNECT;

public class DisconnectCommand extends Command {
    public DisconnectCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return DISCONNECT + System.lineSeparator();
    }
}
