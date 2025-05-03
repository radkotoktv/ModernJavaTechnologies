package command;

import file.writer.PlaylistWriter;
import playlist.Playlist;

import java.util.List;

import static communication.ConnectConstants.PLAYLISTS_PATH;
import static communication.ResponseConstants.PLAYLIST_ALREADY_EXISTS;
import static communication.ResponseConstants.SUCCESSFUL_PLAYLIST_CREATION;

public class CreatePlaylistCommand extends Command {
    private final String name;
    private final String owner;

    public CreatePlaylistCommand(String[] args) {
        super(args);
        name = args[0];
        owner = args[1];
    }

    public CreatePlaylistCommand(String[] args, List<Playlist> playlists) {
        super(args, List.of(), List.of(), playlists);
        name = args[0];
        owner = args[1];
    }

    @Override
    public String execute() {
        Playlist newPlaylist = new Playlist(name,
                owner,
                0,
                0,
                List.of(),
                0);
        boolean alreadyExists = playlists.stream()
                .anyMatch(p -> p.name().equals(name));

        if (alreadyExists) {
            return PLAYLIST_ALREADY_EXISTS;
        }
        playlists.add(newPlaylist);
        PlaylistWriter.getInstance(PLAYLISTS_PATH).writeToFile(newPlaylist);

        return SUCCESSFUL_PLAYLIST_CREATION;
    }
}