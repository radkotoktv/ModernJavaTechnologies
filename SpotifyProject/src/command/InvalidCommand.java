package command;

public class InvalidCommand extends Command {
    public InvalidCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return "Invalid Command!";
    }
}
