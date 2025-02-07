package server;

import playlist.Playlist;
import song.Song;
import user.User;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;

import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import static constants.Constant.USERS_READER;
import static constants.Constant.SONGS_READER;
import static constants.Constant.PLAYLIST_READER;
import static constants.Constant.USER_WRITER;
import static constants.Constant.PLAYLIST_WRITER;
import static constants.Constant.PORT;
import static constants.Constant.BUFFER_SIZE;
import static constants.Constant.SUCCESSFUL_REGISTRATION;
import static constants.Constant.SUCCESSFUL_LOGIN;
import static constants.Constant.SUCCESSFUL_PLAYLIST_CREATION;
import static constants.Constant.SUCCESSFUL_SONG_ADDITION;
import static constants.Constant.PRINT_USER;
import static constants.Constant.SUCCESSFUL_LOGOUT;
import static constants.Constant.UNSUCCESSFUL_PLAYLIST_SHOW;

public class Server {
    private static ArrayList<User> users;
    private static ArrayList<Song> songs;
    private static ArrayList<Playlist> playlists;

    public static void main(String... args) throws IOException {
        users = USERS_READER.readFromFile();
        songs = SONGS_READER.readFromFile();
        playlists = PLAYLIST_READER.readFromFile();
        createServer();
    }

    public static String registerUser(User newUser) {
        if (users.contains(newUser)) {
            return "User already exists!";
        }
        users.add(newUser);
        USER_WRITER.writeToFile(newUser);
        return SUCCESSFUL_REGISTRATION;
    }

    public static String login(String password, String email) {
        User toLogin = new User(password, email);
        if (!users.contains(toLogin)) {
            return "User not found!";
        }
        return SUCCESSFUL_LOGIN;
    }

    public static String addPlaylist(Playlist newPlaylist) {
        if (playlists.contains(newPlaylist)) {
            return "Playlist already exists!";
        }
        playlists.add(newPlaylist);
        PLAYLIST_WRITER.writeToFile(newPlaylist);

        return SUCCESSFUL_PLAYLIST_CREATION;
    }

    public static String handleSongAddition(String playlistName, String songName, String ownerEmail) {
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
                .filter(s -> s.title().equals(songName)).
                findFirst()
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

    public static String showPlaylist(String playlistName) {
        Playlist playlist = playlists.stream()
                .filter(p -> p.name().equals(playlistName))
                .findFirst()
                .orElse(null);
        if (playlist == null) {
            return UNSUCCESSFUL_PLAYLIST_SHOW;
        }

        StringBuilder returnString = new StringBuilder(playlist.name() + " " + playlist.owner() + " " + playlist.duration() + " " + playlist.numberOfSongs() + " Songs: {");
        for (Song song : playlist.songs()) {
            returnString.append(song.title()).append(" ").append(song.artist()).append(" ");
        }
        return returnString + "}";
    }

    public static String top(int numberOfSongs) {
        StringBuilder returnString = new StringBuilder("Top " + numberOfSongs + " songs:\n");
        songs.stream()
                .sorted((s1, s2) -> s2.numberOfPlays() - s1.numberOfPlays())
                .limit(numberOfSongs)
                .forEach(s -> returnString.append(s.title()).append(" ").append(s.artist()).append(" ").append(s.numberOfPlays()).append("\n"));
        return returnString.toString();
    }

    public static String search(String... words) {
        StringBuilder returnString = new StringBuilder("Songs containing ");
        for (String word : words) {
            returnString.append(word).append(" ");
        }
        returnString.append(":\n");
        songs.stream()
                .filter(s -> {
                    for (String word : words) {
                        if (!s.title().contains(word) && !s.artist().contains(word)) {
                            return false;
                        }
                    }
                    return true;
                })
                .forEach(s -> returnString.append(s.title()).append(" ").append(s.artist()).append(" ").append(s.numberOfPlays()).append("\n"));
        return returnString.toString();
    }

    public static String handleCommand(String[] receivedData) {
        return switch (receivedData[0]) {
            case "register" -> registerUser(new User(receivedData[1], receivedData[2]));
            case "login" -> login(receivedData[1], receivedData[2]);
            case "disconnect" -> "You have selected the disconnect option!";
            case "search" -> search();
            case "top" -> top(Integer.parseInt(receivedData[1]));
            case "create-playlist" -> addPlaylist(new Playlist(receivedData[1], receivedData[2],0, 0, new ArrayList<>(), 0));
            case "add-song-to" -> handleSongAddition(receivedData[1], receivedData[2], receivedData[3]);
            case "show-playlist" -> showPlaylist(receivedData[1]);
            case "play" -> "You have selected the play option!";
            case "stop" -> "You have selected the stop option!";
            case "user" -> PRINT_USER;
            case "logout" -> SUCCESSFUL_LOGOUT;
            default -> "Invalid command!";
        };
    }

    public static void createServer() throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocket.bind(new InetSocketAddress(Integer.parseInt(PORT.getValue())));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server.Server started on port " + PORT.getValue());

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverSocket.accept();
                        if (clientChannel != null) {
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
                        }
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(Integer.parseInt(BUFFER_SIZE.getValue()));
                        try {
                            int bytesRead = clientChannel.read(buffer);
                            if (bytesRead == -1) {
                                System.out.println("client.Client disconnected: " + clientChannel.getRemoteAddress());
                                clientChannel.close();
                                key.cancel();
                                continue;
                            }
                            buffer.flip();
                            byte[] requestData = new byte[buffer.remaining()];
                            buffer.get(requestData);
                            String[] receivedData = new String(requestData).split(" ");
                            try {
                                String response = handleCommand(receivedData);
                                buffer.clear();
                                buffer.put(response.getBytes());
                                buffer.flip();
                                clientChannel.write(buffer);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input received from client: " + receivedData);
                            }
                        } catch (IOException e) {
                            System.out.println("Error handling client: " + e.getMessage());
                            key.cancel();
                            clientChannel.close();
                        }
                    }
                }
            }
        }
    }
}