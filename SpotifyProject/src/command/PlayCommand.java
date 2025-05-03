package command;

import song.Song;

import java.util.List;

import static communication.ResponseConstants.SONG_NOT_FOUND;

public class PlayCommand extends Command {
    private final String songName;

    public PlayCommand(String[] args) {
        super(args);
        songName = args[0];
    }

    public PlayCommand(String[] args, List<Song> songs) {
        super(args, List.of(), songs, List.of());
        songName = args[0];
    }

    @Override
    public String execute() {
        if (songName == null) {
            return SONG_NOT_FOUND;
        }

        Song song = songs.stream()
                .filter(s -> s.title().equals(songName))
                .findFirst()
                .orElse(null);

        if (song == null) {
            return SONG_NOT_FOUND;
        }

        song.setNumberOfPlays(song.numberOfPlays() + 1);
        return song.fileName();
    }
}
