import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerTest {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 7777;
    private static final int BUFFER_SIZE = 1024;

    public static void createServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            Selector selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            int readyChannels = selector.select();
            if (readyChannels == 0) {
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isReadable()) {
                    // A channel is ready for reading
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (true) {
                        buffer.clear();
                        int r = sc.read(buffer);
                        if (r <= 0) {
                            continue;
                        }
                        buffer.flip();
                        sc.write(buffer);
                    }
                } else if (key.isWritable()) {
                    SocketChannel socketChannel2 = SocketChannel.open();
                    socketChannel2.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

                    String newData = "Current time: " + System.currentTimeMillis();
                    ByteBuffer buf = ByteBuffer.allocate(48);

                    buf.put(newData.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()) {
                        socketChannel2.write(buf);
                    }
                }
                keyIterator.remove();
            }
        }
    }

    public static void main(String... args) throws IOException {
        createServer();
    }
}
