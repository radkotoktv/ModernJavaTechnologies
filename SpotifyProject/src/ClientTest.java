import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    public static void createClient() {
        try {
            InetSocketAddress serverAddress = new InetSocketAddress(SERVER_HOST, SERVER_PORT);
            SocketChannel socketChannel = SocketChannel.open(serverAddress);

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            String message = "Hello, Server!";
            buffer.put(message.getBytes());
            buffer.flip();
            socketChannel.write(buffer);

            buffer.clear();
            int bytesRead = socketChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                byte[] responseBytes = new byte[buffer.remaining()];
                buffer.get(responseBytes);
                String response = new String(responseBytes);
                System.out.println("Received from server: " + response);
            }

            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createClient();
    }
}