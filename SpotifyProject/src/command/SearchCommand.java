package command;

public class SearchCommand extends Command {
    public SearchCommand(String[] args) {
        super(args);
    }

    @Override
    public String execute() {
        StringBuilder returnString = new StringBuilder("Songs containing ");
        for (String word : args) {
            returnString.append(word).append(" ");
        }
        returnString.append(":\n");
        songs.stream()
                .filter(s -> {
                    for (String word : args) {
                        if (!s.title().contains(word) && !s.artist().contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .forEach(s -> returnString.append(s.toString()).append("\n"));
        return returnString.toString();
    }
}
