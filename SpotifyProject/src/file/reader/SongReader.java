package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.FileReaderException;
import song.Song;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class SongReader extends Reader {
    private static volatile SongReader instance;

    private SongReader(String filePath) {
        super(filePath);
    }

    public static SongReader getInstance(String filePath) {
        SongReader result = instance;
        if (result != null) {
            return result;
        }

        synchronized (SongReader.class) {
            if (instance == null) {
                instance = new SongReader(filePath);
            }
            return instance;
        }
    }

    @Override
    public ArrayList<Song> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Song>>() {

            }.getType());
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in SongReader");
        }
    }
}
