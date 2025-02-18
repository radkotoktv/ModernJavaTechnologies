package command;

import playlist.Playlist;

import static communication.ResponseConstants.UNSUCCESSFUL_PLAYLIST_SHOW;

public class ShowPlaylistCommand extends Command {
    private final String playlistName;

    public ShowPlaylistCommand(String[] args) {
        super(args);
        playlistName = args[0];
    }

    @Override
    public String execute() {
        Playlist playlist = playlists.stream()
                .filter(p -> p.name().equals(playlistName))
                .findFirst()
                .orElse(null);
        if (playlist == null) {
            return UNSUCCESSFUL_PLAYLIST_SHOW;
        }

        return playlist.toString();
    }
}
