package command;

import file.writer.PlaylistWriter;
import playlist.Playlist;

import java.util.List;

import static communication.ConnectConstants.PLAYLISTS_PATH;
import static communication.ResponseConstants.SUCCESSFUL_PLAYLIST_CREATION;

public class CreatePlaylistCommand extends Command {
    private final String name;
    private final String owner;

    public CreatePlaylistCommand(String[] args) {
        super(args);
        this.name = args[0];
        this.owner = args[1];
    }

    @Override
    public String execute() {
        Playlist newPlaylist = new Playlist(name,
                owner,
                0,
                0,
                List.of(),
                0);
        if (playlists.contains(newPlaylist)) {
            return "Playlist already exists!";
        }
        playlists.add(newPlaylist);
        PlaylistWriter.getInstance(PLAYLISTS_PATH).writeToFile(newPlaylist);

        return SUCCESSFUL_PLAYLIST_CREATION;
    }
}