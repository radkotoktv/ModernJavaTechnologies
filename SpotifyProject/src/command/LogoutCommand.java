package command;

import static communication.ResponseConstants.SUCCESSFUL_LOGOUT;

public class LogoutCommand extends Command {
    public LogoutCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return SUCCESSFUL_LOGOUT;
    }
}
