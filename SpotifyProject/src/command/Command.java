package command;

import file.reader.PlaylistReader;
import file.reader.SongReader;
import file.reader.UsersReader;
import playlist.Playlist;
import song.Song;
import user.User;

import java.util.List;

import static communication.ConnectConstants.USERS_PATH;
import static communication.ConnectConstants.SONGS_PATH;
import static communication.ConnectConstants.PLAYLISTS_PATH;

public abstract class Command {
    protected final String[] args;
    protected final List<User> users;
    protected final List<Song> songs;
    protected final List<Playlist> playlists;

    public Command(String[] args) {
        this.args = args;
        this.users = UsersReader.getInstance(USERS_PATH).readFromFile();
        this.songs = SongReader.getInstance(SONGS_PATH).readFromFile();
        this.playlists = PlaylistReader.getInstance(PLAYLISTS_PATH).readFromFile();
    }

    public static Command handleCommand(String[] command) {
        String[] args = new String[command.length - 1];
        System.arraycopy(command, 1, args, 0, command.length - 1);

        return switch (command[0]) {
            case "register" -> new RegisterCommand(args);
            case "login" -> new LoginCommand(args);
            case "disconnect" -> new DisconnectCommand(args);
            case "search" -> new SearchCommand(args);
            case "top" -> new TopCommand(args);
            case "create-playlist" -> new CreatePlaylistCommand(args);
            case "add-song-to" -> new AddSongToPlaylistCommand(args);
            case "show-playlist" -> new ShowPlaylistCommand(args);
            case "show-songs" -> new ShowSongsCommand(args);
            case "play" -> new PlayCommand(args);
            case "stop" -> new StopCommand(args);
            case "user" -> new UserCommand(args);
            case "logout" -> new LogoutCommand(args);
            case "help" -> new HelpCommand(args);
            default -> new InvalidCommand(args);
        };
    }

    public abstract String execute();
}
