package command;

import playlist.Playlist;
import song.Song;

import java.util.List;

import static communication.ResponseConstants.SONG_NOT_FOUND;
import static communication.ResponseConstants.SUCCESSFUL_SONG_ADDITION;
import static communication.ResponseConstants.SONG_ALREADY_INSIDE;
import static communication.ResponseConstants.PLAYLIST_NOT_FOUND;
import static communication.ResponseConstants.NO_PERMISSION;

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

    public AddSongToPlaylistCommand(String[] args, List<Song> songs, List<Playlist> playlists) {
        super(args, List.of(), songs, playlists);
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
            return PLAYLIST_NOT_FOUND;
        }

        if (!playlist.owner().equals(ownerEmail)) {
            return NO_PERMISSION;
        }
        Song song = songs.stream()
                .filter(s -> s.title().equals(songName))
                .findFirst()
                .orElse(null);
        if (song == null) {
            return SONG_NOT_FOUND;
        }

        if (playlist.songs().contains(song)) {
            return SONG_ALREADY_INSIDE;
        }

        playlist.addSong(song);
        return SUCCESSFUL_SONG_ADDITION;
    }
}
