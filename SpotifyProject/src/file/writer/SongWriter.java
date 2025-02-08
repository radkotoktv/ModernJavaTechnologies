package file.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.FileReaderException;
import exception.FileWriterException;
import song.Song;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SongWriter implements Writer<Song> {
    @Override
    public void writeToFile(Song toAdd) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Song>>() {

        }.getType();
        List<Song> songList;

        try (FileReader reader = new FileReader("src/data/songs.json")) {
            songList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in SongWriter");
        }
        songList.add(toAdd);

        try (FileWriter writer = new FileWriter("src/data/songs.json")) {
            gson.toJson(songList, writer);
        } catch (IOException e) {
            throw new FileWriterException("Error writing to file in SongWriter");
        }
    }
}
