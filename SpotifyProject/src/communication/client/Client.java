package communication.client;

import exception.DisconnectedFromServerException;
import player.WavPlayer;
import user.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static communication.ConnectConstants.ZERO;
import static communication.ConnectConstants.ONE;
import static communication.ConnectConstants.TWO;
import static communication.ConnectConstants.THREE;
import static communication.ResponseConstants.SUCCESSFUL_LOGIN;
import static communication.ResponseConstants.SUCCESSFUL_LOGOUT;
import static communication.ResponseConstants.UNSUCCESSFUL_PLAYLIST_SHOW;
import static communication.ResponseConstants.SONG_NOT_FOUND;
import static communication.ResponseConstants.STOP_SONG;
import static communication.ConnectConstants.SERVER_HOST;
import static communication.ConnectConstants.PORT;
import static communication.ConnectConstants.BUFFER_SIZE;

public class Client {
    private User currentUser;
    private volatile boolean isPlaying;

    public static void main(String[] args) {
        Client client = new Client();
        client.createClient();
    }

    public Client() {
        currentUser = null;
        isPlaying = false;
    }

    public boolean validateCommand(String[] command) {
        return switch(command[0]) {
            case "register", "login" ->
                    command.length == THREE;
            case "stop", "user", "logout", "show-songs", "help" ->
                    command.length == ONE && currentUser != null;
            case "play", "show-playlist", "create-playlist", "top" ->
                    command.length == TWO && currentUser != null;
            case "add-song-to" ->
                    command.length == THREE && currentUser != null;
            case "search" ->
                    command.length > ONE && currentUser != null;
            case "disconnect" ->
                    command.length == ONE;
            default -> false;
        };
    }

    public void handleLogin(String[] command, String response) {
        if (response.equals(SUCCESSFUL_LOGIN)) {
            currentUser = new User(command[ONE], command[TWO]);
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    public void handleUser() {
        System.out.println(currentUser);
    }

    public void handleDisconnect() {
        System.exit(0);
    }

    public void handleLogout() {
        currentUser = null;
        System.out.println(SUCCESSFUL_LOGOUT);
    }

    public void showPlaylist(String response) {
        if (response.equals(UNSUCCESSFUL_PLAYLIST_SHOW)) {
            System.out.println("Playlist not found!");
        } else {
            System.out.println(response);
        }
    }

    public void handlePlay(String response) {
        if (!isPlaying && !response.equals(SONG_NOT_FOUND)) {
            Thread audioThread = new Thread(new WavPlayer(response));
            audioThread.start();
            isPlaying = true;
        } else {
            System.out.println(response);
        }
    }

    public void handleStop(String response) {
        if (isPlaying && response.equals(STOP_SONG)) {
            System.out.println("Song has been stopped!");
            //audioThread.interrupt();
            isPlaying = false;
        } else {
            System.out.println("No song is playing!");
        }
    }

    public void handleResponse(String[] command, String response) throws InterruptedException {
        switch (command[ZERO]) {
            case "login" -> handleLogin(command, response);
            case "user" -> handleUser();
            case "disconnect" -> handleDisconnect();
            case "logout" -> handleLogout();
            case "show-playlist" -> showPlaylist(response);
            case "play" -> handlePlay(response);
            case "stop" -> handleStop(response);
            default -> System.out.println(response);
        }
    }

    public void createClient() {
        try (SocketChannel socketChannel = SocketChannel.open(); Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, Integer.parseInt(PORT)));
            while (true) {
                System.out.print("Enter a command: ");
                String userInput = scanner.nextLine();
                String[] command = userInput.split(" ");
                if (!validateCommand(command)) {
                    continue;
                }

                userInput += (currentUser != null ? " " + currentUser.email() : "");
                ByteBuffer buffer = ByteBuffer.allocate(Integer.parseInt(BUFFER_SIZE));
                buffer.put(userInput.getBytes());
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();
                int bytesRead = socketChannel.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    byte[] responseData = new byte[buffer.remaining()];
                    buffer.get(responseData);
                    String response = new String(responseData).trim();
                    handleResponse(command, response);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new DisconnectedFromServerException("Disconnected from communication.client.server!", e);
        }
    }
}