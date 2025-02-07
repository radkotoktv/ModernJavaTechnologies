package client;

import user.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static constants.Constant.*;

public class ClientLogic {
    private User currentUser = null;

    public ClientLogic() {
        createClient();
    }

    public boolean validateCommand(String[] command) {
        return switch(command[0]) {
            case "register", "login" -> command.length == 3;
            case "disconnect", "stop", "user", "logout" -> command.length == 1 && currentUser != null;
            case "search", "play", "show-playlist", "create-playlist", "top" -> command.length == 2 && currentUser != null;
            case "add-song-to" -> command.length == 3 && currentUser != null;
            default -> false;
        };
    }

    public void handleResponse(String[] command, String response) {
        switch (command[0]) {
            case "login": {
                if (response.equals(SUCCESSFUL_LOGIN)) {
                    currentUser = new User(command[1], command[2]);
                } else {
                    System.out.println("Invalid credentials!");
                }
                break;
            }
            case "user": {
                System.out.println(currentUser);
                break;
            }
            case "disconnect": {
                System.exit(0);
                break;
            }
            case "logout": {
                currentUser = null;
                System.out.println(SUCCESSFUL_LOGOUT);
                break;
            }
            case "show-playlist": {
                if (response.equals(UNSUCCESSFUL_PLAYLIST_SHOW)) {
                    System.out.println("Playlist not found!");
                } else {
                    System.out.println(response);
                }
            }
            case "search": {
                System.out.println(response);
                break;
            }
        }
    }

    public void createClient() {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST.getValue(), Integer.parseInt(PORT.getValue())));
            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter a command: ");
                String userInput = scanner.nextLine();
                String[] command = userInput.split(" ");
                if (!validateCommand(command)) {
                    continue;
                }

                userInput += (currentUser != null ? " " + currentUser.email() : "");
                ByteBuffer buffer = ByteBuffer.allocate(Integer.parseInt(BUFFER_SIZE.getValue()));
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
                    System.out.println("Received from server: " + response);
                    handleResponse(command, response);
                }
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }
}
