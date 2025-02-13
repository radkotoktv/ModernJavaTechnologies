package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.FileReaderException;
import playlist.Playlist;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class PlaylistReader extends Reader {
    private static volatile PlaylistReader instance;

    private PlaylistReader(String filePath) {
        super(filePath);
    }

    public static PlaylistReader getInstance(String filePath) {
        PlaylistReader result = instance;
        if (result != null) {
            return result;
        }

        synchronized (PlaylistReader.class) {
            if (instance == null) {
                instance = new PlaylistReader(filePath);
            }
            return instance;
        }
    }

    @Override
    public ArrayList<Playlist> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Playlist>>() {

            }.getType());
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in PlaylistReader", e);
        }
    }
}
