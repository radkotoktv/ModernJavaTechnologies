package communication.server;

import command.Command;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;

import java.util.Set;
import java.util.Iterator;

import static communication.ConnectConstants.BUFFER_SIZE;
import static communication.ConnectConstants.PORT;

public class Server {
    public static void main(String... args) throws IOException {
        Server server = new Server();
        server.runServer();
    }

    public Server() {
    }

    public String handleCommand(String[] receivedData) {
        Command command = Command.handleCommand(receivedData);
        return command.execute();
    }

    public void runServer() throws IOException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open(); Selector selector = Selector.open()) {
            configureServerSocket(serverSocket, selector);
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    handleKeys(key, serverSocket, selector);
                }
            }
        }
    }

    public void handleKeys(SelectionKey key,
                                ServerSocketChannel serverSocket,
                                Selector selector) throws IOException {
        if (key.isAcceptable()) {
            SocketChannel clientChannel = serverSocket.accept();
            configureClientChannel(clientChannel, selector);
        } else if (key.isReadable()) {
            SocketChannel clientChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(Integer.parseInt(BUFFER_SIZE));
            try {
                int bytesRead = clientChannel.read(buffer);
                if (bytesRead == -1) {
                    closeConnection(clientChannel, key);
                    return;
                }
                buffer.flip();
                byte[] requestData = new byte[buffer.remaining()];
                buffer.get(requestData);
                String[] receivedData = new String(requestData).split(" ");
                try {
                    String response = handleCommand(receivedData);
                    bufferLogic(buffer, response);
                    clientChannel.write(buffer);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input received from communication.client: " + receivedData);
                }
            } catch (IOException e) {
                System.out.println("Error handling communication.client: " + e.getMessage());
                closeConnection(clientChannel, key);
            }
        }
    }

    public void bufferLogic(ByteBuffer buffer, String response) {
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
    }

    public void configureServerSocket(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        serverSocket.bind(new InetSocketAddress(Integer.parseInt(PORT)));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port " + PORT);
    }

    public void configureClientChannel(SocketChannel clientChannel, Selector selector) throws IOException {
        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
        }
    }

    public void closeConnection(SocketChannel clientChannel, SelectionKey key) throws IOException {
        System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
        clientChannel.close();
        key.cancel();
    }
}