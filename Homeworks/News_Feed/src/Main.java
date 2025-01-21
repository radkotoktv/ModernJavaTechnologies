import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
//import java.io.FileReader;
//import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String HOST = "google.com";
    private static final int HTTP_PORT = 80;
    private static final String HTTP_REQUEST = "GET / http/1.1" + System.lineSeparator();

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, HTTP_PORT)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(HTTP_REQUEST);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not access web site", e);
        }
    }
}