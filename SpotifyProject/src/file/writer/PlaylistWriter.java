package file.writer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exception.FileReaderException;
import exception.FileWriterException;
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
        List<Playlist> playlistList;

        try (FileReader reader = new FileReader("src/data/playlists.json")) {
            playlistList = gson.fromJson(reader, listType);
            if (playlistList == null) {
                playlistList = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new FileReaderException("Error reading from file in PlaylistWriter");
        }

        setAttributes(playlistList, toAdd, gson);

        playlistList.add(toAdd);

        try (FileWriter writer = new FileWriter("src/data/playlists.json")) {
            gson.toJson(playlistList, writer);
        } catch (IOException e) {
            throw new FileWriterException("Error writing to file in PlaylistWriter");
        }
    }

    public void setAttributes(List<Playlist> playlistList, Playlist toAdd, Gson gson) {
        for (Playlist playlist : playlistList) {
            if (playlist.name().equals(toAdd.name()) && playlist.owner().equals(toAdd.owner())) {
                playlist.setSongs(toAdd.songs());
                playlist.setDuration(toAdd.duration());
                playlist.setNumberOfSongs(toAdd.numberOfSongs());
                playlist.setAmountOfPlays(toAdd.amountOfPlays());

                try (FileWriter writer = new FileWriter("src/data/playlists.json")) {
                    gson.toJson(playlistList, writer);
                } catch (IOException e) {
                    throw new FileWriterException("Error writing to file in PlaylistWriter's setAttributes method");
                }
            }
        }
    }
}
