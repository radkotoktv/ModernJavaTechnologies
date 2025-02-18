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
import java.util.List;

public final class PlaylistWriter extends Writer<Playlist> {
    private static volatile PlaylistWriter instance;

    private PlaylistWriter(String filePath) {
        super(filePath);
    }

    public static PlaylistWriter getInstance(String filePath) {
        PlaylistWriter result = instance;
        if (result != null) {
            return result;
        }

        synchronized (PlaylistWriter.class) {
            if (instance == null) {
                instance = new PlaylistWriter(filePath);
            }
            return instance;
        }
    }

    @Override
    public void writeToFile(Playlist toAdd) {
        Gson gson = new Gson(); // Move above
        Type listType = new TypeToken<List<Playlist>>() {

        }.getType();
        List<Playlist> playlistList;

        try (FileReader reader = new FileReader(filePath)) {
            playlistList = gson.fromJson(reader, listType);
            if (playlistList == null) {
                playlistList = List.of();
            }
        } catch (IOException | FileReaderException e) {
            throw new FileReaderException("Error reading from file in PlaylistWriter", e);
        }

        setAttributes(playlistList, toAdd, gson);

        playlistList.add(toAdd);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(playlistList, writer);
        } catch (IOException e) {
            throw new FileWriterException("Error writing to file in PlaylistWriter", e);
        }
    }

    public void setAttributes(List<Playlist> playlistList, Playlist toAdd, Gson gson) {
        for (Playlist playlist : playlistList) {
            if (playlist.name().equals(toAdd.name()) && playlist.owner().equals(toAdd.owner())) {
                playlist.setSongs(toAdd.songs());
                playlist.setDuration(toAdd.duration());
                playlist.setNumberOfSongs(toAdd.numberOfSongs());
                playlist.setAmountOfPlays(toAdd.amountOfPlays());

                try (FileWriter writer = new FileWriter(filePath)) {
                    gson.toJson(playlistList, writer);
                } catch (IOException e) {
                    throw new FileWriterException("Error writing to file in PlaylistWriter's setAttributes method", e);
                }
            }
        }
    }
}
