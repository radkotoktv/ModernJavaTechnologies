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
import java.util.List;
import java.util.stream.Collectors;

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
import static constants.Constant.HELP_TEXT;
import static constants.Constant.STOP_SONG;
import static constants.Constant.SONG_NOT_FOUND;
import static constants.Constant.ONE;
import static constants.Constant.TWO;
import static constants.Constant.THREE;

public class Server {
    private static ArrayList<User> users;
    private static ArrayList<Song> songs;
    private static ArrayList<Playlist> playlists;

    public static void main(String... args) throws IOException {
        Server server = new Server();
        server.runServer();
    }

    public Server() {
        users = USERS_READER.readFromFile();
        songs = SONGS_READER.readFromFile();
        playlists = PLAYLIST_READER.readFromFile();
    }

    public String registerUser(User newUser) {
        if (users.contains(newUser)) {
            return "User already exists!";
        }
        users.add(newUser);
        USER_WRITER.writeToFile(newUser);
        return SUCCESSFUL_REGISTRATION;
    }

    public String login(String password, String email) {
        User toLogin = new User(password, email);
        if (!users.contains(toLogin)) {
            return "User not found!";
        }
        return SUCCESSFUL_LOGIN;
    }

    public String addPlaylist(Playlist newPlaylist) {
        if (playlists.contains(newPlaylist)) {
            return "Playlist already exists!";
        }
        playlists.add(newPlaylist);
        PLAYLIST_WRITER.writeToFile(newPlaylist);

        return SUCCESSFUL_PLAYLIST_CREATION;
    }

    public String handleSongAddition(String playlistName, String songName, String ownerEmail) {
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

    public String showPlaylist(String playlistName) {
        Playlist playlist = playlists.stream()
                .filter(p -> p.name().equals(playlistName))
                .findFirst()
                .orElse(null);
        if (playlist == null) {
            return UNSUCCESSFUL_PLAYLIST_SHOW;
        }

        return playlist.toString();
    }

    public String top(int numberOfSongs) {
        StringBuilder returnString = new StringBuilder("Top " + numberOfSongs + " songs:\n");
        songs.stream()
                .sorted((s1, s2) -> s2.numberOfPlays() - s1.numberOfPlays())
                .limit(numberOfSongs)
                .forEach(s -> returnString.append(s).append("\n"));
        return returnString.toString();
    }

    public List<String> parseInput(String[] words) {
        return java.util.Arrays.stream(words, ONE, words.length - 1)
                .collect(Collectors.toList());
    }

    public String search(List<String> words) {
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
                .forEach(s -> returnString.append(s.toString()).append("\n"));
        return returnString.toString();
    }

    public String play(String songName) {
        if (songName == null) {
            return SONG_NOT_FOUND;
        }

        Song song = songs.stream()
                .filter(s -> s.title().equals(songName))
                .findFirst()
                .orElse(null);

        if (song == null) {
            return SONG_NOT_FOUND;
        }

        song.setNumberOfPlays(song.numberOfPlays() + 1);
        return song.fileName();
    }

    public String handleCommand(String[] receivedData) {
        return switch (receivedData[0]) {
            case "register" -> registerUser(new User(receivedData[ONE], receivedData[TWO]));
            case "login" -> login(receivedData[ONE], receivedData[TWO]);
            case "disconnect" -> "You have selected the disconnect option!";
            case "search" -> search(parseInput(receivedData));
            case "top" -> top(Integer.parseInt(receivedData[ONE]));
            case "create-playlist" -> addPlaylist(new Playlist(receivedData[ONE],
                    receivedData[TWO],
                    0,
                    0,
                    new ArrayList<>(),
                    0));
            case "add-song-to" -> handleSongAddition(receivedData[ONE], receivedData[TWO], receivedData[THREE]);
            case "show-playlist" -> showPlaylist(receivedData[ONE]);
            case "show-songs" -> songs.toString();
            case "play" -> play(receivedData[ONE]);
            case "stop" -> STOP_SONG;
            case "user" -> PRINT_USER;
            case "logout" -> SUCCESSFUL_LOGOUT;
            case "help" -> HELP_TEXT;
            default -> "Invalid command!";
        };
    }

    public void runServer() throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open(); Selector selector = Selector.open()) {
            configureServerSocket(serverSocket, selector);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    keyLogic(key, serverSocket, selector);
                }
            }
        }
    }

    public void keyLogic(SelectionKey key,
                                ServerSocketChannel serverSocket,
                                Selector selector) throws IOException {
        if (key.isAcceptable()) {
            SocketChannel clientChannel = serverSocket.accept();
            configureClientChannel(clientChannel, selector);
        } else if (key.isReadable()) {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(Integer.parseInt(BUFFER_SIZE.getValue()));
            try {
                int bytesRead = clientChannel.read(buffer);
                if (bytesRead == -1) {
                    closeConnection(clientChannel, key);
                    return;
                }
                buffer.flip();
                byte[] requestData = new byte[buffer.remaining()];
                buffer.get(requestData);
                String[] receivedData = new String(requestData).split(" ");
                try {
                    String response = handleCommand(receivedData);
                    bufferLogic(buffer, response);
                    clientChannel.write(buffer);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input received from client: " + receivedData);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
                closeConnection(clientChannel, key);
            }
        }
    }

    public void bufferLogic(ByteBuffer buffer, String response) {
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
    }

    public void configureServerSocket(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        serverSocket.bind(new InetSocketAddress(Integer.parseInt(PORT.getValue())));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port " + PORT.getValue());
    }

    public void configureClientChannel(SocketChannel clientChannel, Selector selector) throws IOException {
        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
        }
    }

    public void closeConnection(SocketChannel clientChannel, SelectionKey key) throws IOException {
        System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
        clientChannel.close();
        key.cancel();
    }
}