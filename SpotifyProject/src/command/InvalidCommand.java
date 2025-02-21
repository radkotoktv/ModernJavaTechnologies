package command;

import static communication.ResponseConstants.INVALID_COMMAND;

public class InvalidCommand extends Command {
    public InvalidCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return INVALID_COMMAND;
    }
}
