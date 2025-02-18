package command;

import playlist.Playlist;
import song.Song;

import static communication.ResponseConstants.SUCCESSFUL_SONG_ADDITION;

public class AddSongToPlaylistCommand extends Command {
    private final String playlistName;
    private final String songName;
    private final String ownerEmail;

    public AddSongToPlaylistCommand(String[] args) {
        super(args);
        playlistName = args[0];
        songName = args[1];
        ownerEmail = args[2];
    }

    @Override
    public String execute() {
        Playlist playlist = playlists.stream()
                .filter(p -> p.name().equals(playlistName))
                .findFirst()
                .orElse(null);
        if (playlist == null) {
            return "Playlist not found!";
        }

        if (!playlist.owner().equals(ownerEmail)) {
            return "You are not the owner of this playlist!";
        }
        Song song = songs.stream()
                .filter(s -> s.title().equals(songName))
                .findFirst()
                .orElse(null);
        if (song == null) {
            return "Song not found!";
        }

        if (playlist.songs().contains(song)) {
            return "Song is already in the playlist!";
        }

        playlist.addSong(song);
        return SUCCESSFUL_SONG_ADDITION;
    }
}
