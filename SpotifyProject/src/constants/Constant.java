package constants;

public enum Constant { // Can be multiple files
    PORT("7777"),
    BUFFER_SIZE("1024"),
    SERVER_HOST("localhost");

    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    public static final int WAV_BUFFER_SIZE = 4096;

    public static final String USERS_PATH = "src/data/users.json";
    public static final String SONGS_PATH = "src/data/songs.json";
    public static final String PLAYLISTS_PATH = "src/data/playlists.json";

    public static final String SUCCESSFUL_REGISTRATION = "You have been registered to the system!";
    public static final String SUCCESSFUL_LOGIN = "You have successfully logged in!";
    public static final String SUCCESSFUL_SONG_ADDITION = "Song has been added successfully!";
    public static final String SUCCESSFUL_PLAYLIST_CREATION = "Playlist has been created successfully!";
    public static final String SUCCESSFUL_LOGOUT = "You have successfully logged out!";
    public static final String UNSUCCESSFUL_PLAYLIST_SHOW = "Playlist not found!";
    public static final String STOP_SONG = "Song has been stopped!";
    public static final String SONG_NOT_FOUND = "Song not found!";
    public static final String PRINT_USER = "Request the client to print the current user's information.";
    public static final String HELP_TEXT = "Available commands:\n" +
            "register <email> <password> - registers a new user\n" +
            "login <email> <password> - logs in the user\n" +
            "logout - logs out the user\n" +
            "user - prints the current user's information\n" +
            "search <words> - searches for a song which has <words> in it's title or artist name\n" +
            "top <number> - shows the top <number> songs\n" +
            "show-playlist <name_of_the_playlist> - shows a playlist\n" +
            "create-playlist <name_of_the_playlist> - creates a playlist\n" +
            "add-song-to <name_of_the_playlist> <song> - adds a song to the playlist\n" +
            "show-songs - shows all the songs in the system\n" +
            "play <song> - plays the song\n" +
            "stop - stops the playing song\n" +
            "help - shows this message\n" +
            "disconnect - disconnects the client from the server";

    private final String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
