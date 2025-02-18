package command;

public class DisconnectCommand extends Command {
    public DisconnectCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return "You have selected the disconnect option!" + System.lineSeparator();
    }
}
