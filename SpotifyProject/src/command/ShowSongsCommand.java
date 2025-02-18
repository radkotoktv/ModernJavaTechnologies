package command;

public class ShowSongsCommand extends Command {
    public ShowSongsCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        return songs.toString();
    }
}
