package command;

import song.Song;

import java.util.List;

public class ShowSongsCommand extends Command {
    public ShowSongsCommand(String[] args) {
        super(args);
    }

    public ShowSongsCommand(String[] args, List<Song> songs) {
        super(args, List.of(), songs, List.of());
    }

    @Override
    public String execute() {
        return songs.toString();
    }
}
