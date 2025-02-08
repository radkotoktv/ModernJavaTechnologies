package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import exception.FileReaderException;
import song.Song;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SongReader implements Reader {
    @Override
    public ArrayList<Song> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/data/songs.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Song>>() {

            }.getType());
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in SongReader");
        }
    }
}
