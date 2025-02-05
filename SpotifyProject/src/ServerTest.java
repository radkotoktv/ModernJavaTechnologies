import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerTest {
    private static final int PORT = 7777;
    private static final int BUFFER_SIZE = 1024;
    private static final int INTERVAL = 10000;

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocket.bind(new InetSocketAddress(PORT));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Time server started on port " + PORT);

            while (true) {
                selector.select(INTERVAL);
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
                        int bytesRead = clientChannel.read(buffer);
                        if (bytesRead > 0) {
                            buffer.flip();
                            byte[] requestData = new byte[buffer.remaining()];
                            buffer.get(requestData);
                            int response = Integer.parseInt(new String(requestData)) + 1;
                            System.out.println("Server said: " + response);
                            buffer.clear();
                            buffer.put(String.valueOf(response).getBytes());
                            buffer.flip();
                            clientChannel.write(buffer);
                        }
                    }
                }
            }
        }
    }
}