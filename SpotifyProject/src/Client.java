import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open();
             Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to the server.");

            while (true) {
                System.out.print("Enter a command: ");
                String userInput = scanner.nextLine();

                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
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
                }
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }
}