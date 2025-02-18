package command;

import static communication.ResponseConstants.PRINT_USER;

public class UserCommand extends Command {
    public UserCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return PRINT_USER;
    }
}
