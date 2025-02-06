package file.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import playlist.Playlist;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlaylistWriter implements Writer<Playlist> {
    @Override
    public void writeToFile(Playlist toAdd) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Playlist>>() {

        }.getType();
        List<Playlist> playlistList = new ArrayList<>();

        try (FileReader reader = new FileReader("src/data/playlists.json")) {
            playlistList = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlistList.add(toAdd);

        try (FileWriter writer = new FileWriter("src/data/playlists.json")) {
            gson.toJson(playlistList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
