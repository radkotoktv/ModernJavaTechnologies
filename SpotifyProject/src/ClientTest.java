import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to the server.");

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.put("1".getBytes());
            buffer.flip();
            socketChannel.write(buffer);

            while (true) {
                buffer.clear();
                int bytesRead = socketChannel.read(buffer);

                if (bytesRead > 0) {
                    buffer.flip();
                    byte[] responseData = new byte[buffer.remaining()];
                    buffer.get(responseData);
                    int response = Integer.parseInt(new String(responseData)) + 5;
                    System.out.println("Client said: " + response);
                    buffer.clear();
                    buffer.put(String.valueOf(response).getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
