package communication;

public class ResponseConstants {
    public static final String SUCCESSFUL_REGISTRATION = "You have been registered to the system!";
    public static final String SUCCESSFUL_LOGIN = "You have successfully logged in!";
    public static final String SUCCESSFUL_SONG_ADDITION = "Song has been added successfully!";
    public static final String SUCCESSFUL_PLAYLIST_CREATION = "Playlist has been created successfully!";
    public static final String SUCCESSFUL_LOGOUT = "You have successfully logged out!";
    public static final String UNSUCCESSFUL_PLAYLIST_SHOW = "Playlist not found!";
    public static final String STOP_SONG = "Song has been stopped!";
    public static final String SONG_NOT_FOUND = "Song not found!";
    public static final String PRINT_USER = "Request the communication.client to print the current user's information.";
    public static final String HELP_TEXT = "Available commands:" +
            System.lineSeparator() +
            "register <email> <password> - registers a new user" +
            System.lineSeparator() +
            "login <email> <password> - logs in the user" +
            System.lineSeparator() +
            "logout - logs out the user" +
            System.lineSeparator() +
            "user - prints the current user's information" +
            System.lineSeparator() +
            "search <words> - searches for a song which has <words> in it's title or artist name" +
            System.lineSeparator() +
            "top <number> - shows the top <number> songs" +
            System.lineSeparator() +
            "show-playlist <name_of_the_playlist> - shows a playlist" +
            System.lineSeparator() +
            "create-playlist <name_of_the_playlist> - creates a playlist" +
            System.lineSeparator() +
            "add-song-to <name_of_the_playlist> <song> - adds a song to the playlist" +
            System.lineSeparator() +
            "show-songs - shows all the songs in the system" +
            System.lineSeparator() +
            "play <song> - plays the song" +
            System.lineSeparator() +
            "stop - stops the playing song" +
            System.lineSeparator() +
            "help - shows this message" +
            System.lineSeparator() +
            "disconnect - disconnects the communication.client from the communication.client.server";
}
