package command;

import static communication.ResponseConstants.HELP_TEXT;

public class HelpCommand extends Command {
    public HelpCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return HELP_TEXT;
    }
}
