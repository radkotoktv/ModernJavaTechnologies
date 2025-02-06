package playlist;

import file.writer.PlaylistWriter;
import song.Song;

import java.util.ArrayList;

public record Playlist(String name,
                       String owner,
                       int duration,
                       int numberOfSongs,
                       ArrayList<Song> songs,
                       int amountOfPlayes) {
    public void addSong(Song newSong) {
        if (songs.contains(newSong)) {
            System.out.println("Song is already in the playlist");
            return;
        }
        songs.add(newSong);
        PlaylistWriter playlistWriter = new PlaylistWriter();
        playlistWriter.writeToFile(this);
    }
}
