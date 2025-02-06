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
import static constants.Constant.SONG_WRITER;
import static constants.Constant.PLAYLIST_WRITER;
import static constants.Constant.PORT;
import static constants.Constant.BUFFER_SIZE;

public class Server {
    private static ArrayList<User> users;
    private static ArrayList<Song> songs;
    private static ArrayList<Playlist> playlists;
//    private static HashMap<User, ArrayList<Playlist>> data;

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
        return "You have been registered to the system!";
    }

    public static String login(String password, String email) {
        return null;
    }

    public static void addSong(Song newSong) {
        if (songs.contains(newSong)) {
            System.out.println("Song already exists");
            return;
        }
        songs.add(newSong);
        SONG_WRITER.writeToFile(newSong);
    }

    public static String addPlaylist(Playlist newPlaylist) {
        if (playlists.contains(newPlaylist)) {
            return "Playlist already exists!";
        }
        playlists.add(newPlaylist);
        PLAYLIST_WRITER.writeToFile(newPlaylist);
        return "Playlist successfully created!";
    }

    public static String handleCommand(String[] receivedData) {
        return switch (receivedData[0]) {
            case "register" -> registerUser(new User(receivedData[1], receivedData[2]));
            case "login" -> login(receivedData[1], receivedData[2]);
            case "disconnect" -> "You have selected the disconnect option!";
            case "search" -> "You have selected the search option!";
            case "top" -> "You have selected the top option!";
            case "create-playlist" -> "You have selected the create-playlist option!";
            case "add-song-to" -> "You have selected the add-song-to option!";
            case "show-playlist" -> "You have selected the show-playlist option!";
            case "play" -> "You have selected the play option!";
            case "stop" -> "You have selected the stop option!";
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
                                System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
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