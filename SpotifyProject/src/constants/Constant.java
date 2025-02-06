package constants;

import file.reader.PlaylistReader;
import file.reader.SongReader;
import file.reader.UsersReader;
import file.writer.PlaylistWriter;
import file.writer.SongWriter;
import file.writer.UserWriter;

public enum Constant {
    PORT("7777"),

    BUFFER_SIZE("1024");

    public static final UsersReader USERS_READER = new UsersReader();

    public static final SongReader SONGS_READER = new SongReader();

    public static final PlaylistReader PLAYLIST_READER = new PlaylistReader();

    public static final UserWriter USER_WRITER = new UserWriter();
    
    public static final SongWriter SONG_WRITER = new SongWriter();

    public static final PlaylistWriter PLAYLIST_WRITER = new PlaylistWriter();

    private final String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
