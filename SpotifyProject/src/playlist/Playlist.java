package playlist;

import song.Song;

import java.util.ArrayList;

import static constants.Constant.PLAYLIST_WRITER;

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
        PLAYLIST_WRITER.writeToFile(this);
    }
}
