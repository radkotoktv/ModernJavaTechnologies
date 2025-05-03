package command;

import song.Song;

import java.util.List;

public class TopCommand extends Command {
    private final int numberOfSongs;

    public TopCommand(String[] args) {
        super(args);
        numberOfSongs = Integer.parseInt(args[0]);
    }

    public TopCommand(String[] args, List<Song> songs) {
        super(args, List.of(), songs, List.of());
        numberOfSongs = Integer.parseInt(args[0]);
    }

    @Override
    public String execute() {
        StringBuilder returnString = new StringBuilder("Top " + numberOfSongs + " songs:\n");
        songs.stream()
                .sorted((s1, s2) -> s2.numberOfPlays() - s1.numberOfPlays())
                .limit(numberOfSongs)
                .forEach(s -> returnString.append(s).append("\n"));
        return returnString.toString();
    }
}
