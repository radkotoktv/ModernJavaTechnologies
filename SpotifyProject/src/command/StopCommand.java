package command;

import static communication.ResponseConstants.STOP_SONG;

public class StopCommand extends Command {
    public StopCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return STOP_SONG;
    }
}
