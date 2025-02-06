import file.reader.PlaylistReader;
import file.reader.SongReader;
import file.reader.UsersReader;
import file.writer.PlaylistWriter;
import file.writer.SongWriter;
import file.writer.UserWriter;
import playlist.Playlist;
import song.Song;
import user.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

import static constants.Constant.BUFFER_SIZE;
import static constants.Constant.PORT;

public class ServerTest {
    private static ArrayList<User> users;
    private static ArrayList<Song> songs;
    private static ArrayList<Playlist> playlists;
    private static HashMap<User, ArrayList<Playlist>> data;

    private static final UsersReader USERS_READER = new UsersReader();
    private static final SongReader SONGS_READER = new SongReader();
    private static final PlaylistReader PLAYLIST_READER = new PlaylistReader();
    private static final UserWriter USER_WRITER = new UserWriter();
    private static final SongWriter SONG_WRITER = new SongWriter();
    private static final PlaylistWriter PLAYLIST_WRITER = new PlaylistWriter();

    public static void main(String... args) throws IOException {
        users = USERS_READER.readFromFile();
        songs = SONGS_READER.readFromFile();
        playlists = PLAYLIST_READER.readFromFile();
        createServer();
    }

    public static void registerUser(User newUser) {
        if (users.contains(newUser)) {
            System.out.println("User already exists");
            return;
        }
        users.add(newUser);
        USER_WRITER.writeToFile(newUser);
    }

    public static void addSong(Song newSong) {
        if (songs.contains(newSong)) {
            System.out.println("Song already exists");
            return;
        }
        songs.add(newSong);
        SONG_WRITER.writeToFile(newSong);
    }

    public static void addPlaylist(Playlist newPlaylist) {
        if (playlists.contains(newPlaylist)) {
            System.out.println("Playlist already exists");
            return;
        }
        playlists.add(newPlaylist);
        PLAYLIST_WRITER.writeToFile(newPlaylist);
    }

    public static void createServer() throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocket.bind(new InetSocketAddress(Integer.parseInt(PORT.getValue())));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started on port " + PORT.getValue());

            for (User user : users) {
                System.out.println("User: " + user.username() + ", " + user.password() + ", " + user.email());
            }

            for (Song song : songs) {
                System.out.println("Song: " + song.title() + ", " + song.artist() + ", " + song.duration() + ", " + song.numberOfPlays() + ", " + song.fileName());
            }

            for (Playlist playlist: playlists) {
                System.out.println("Playlist: " + playlist.name() + ", " + playlist.owner() + ", " + playlist.duration() + ", " + playlist.numberOfSongs() + ", " + playlist.songs() + ", " + playlist.amountOfPlayes());
            }

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
                            String receivedData = new String(requestData).trim();

                            try {
                                int receivedValue = Integer.parseInt(receivedData);
                                int response = receivedValue * 7;
                                System.out.println("Received: " + receivedValue + ", sending back: " + response);
                                buffer.clear();
                                buffer.put(String.valueOf(response).getBytes());
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