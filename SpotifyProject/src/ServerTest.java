import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerTest {
    private static final int PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocket.bind(new InetSocketAddress(PORT));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server started on port " + PORT);

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
                        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
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