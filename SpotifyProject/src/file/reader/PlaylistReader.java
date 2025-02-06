package file.reader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import playlist.Playlist;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistReader implements Reader {
    @Override
    public ArrayList<Playlist> readFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/data/playlists.json")) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Playlist>>() {

            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
