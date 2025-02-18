package playlist;

import file.writer.PlaylistWriter;
import song.Song;

import java.util.List;
import java.util.Objects;

import static communication.ConnectConstants.PLAYLISTS_PATH;

public class Playlist {
    private final String name;
    private final String owner;
    private int duration;
    private int numberOfSongs;
    private List<Song> songs;
    private int amountOfPlays;

    public Playlist(String name,
                    String owner,
                    int duration,
                    int numberOfSongs,
                    List<Song> songs,
                    int amountOfPlays) {
        this.name = name;
        this.owner = owner;
        this.duration = duration;
        this.numberOfSongs = numberOfSongs;
        this.songs = songs;
        this.amountOfPlays = amountOfPlays;
    }

    public void addSong(Song newSong) {
        if (songs.contains(newSong)) {
            System.out.println("Song is already in the playlist");
            return;
        }
        songs.add(newSong);
        numberOfSongs++;
        duration += newSong.duration();
        PlaylistWriter.getInstance(PLAYLISTS_PATH).writeToFile(this);
    }

    public String name() {
        return name;
    }

    public String owner() {
        return owner;
    }

    public int duration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int numberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public List<Song> songs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public int amountOfPlays() {
        return amountOfPlays;
    }

    public void setAmountOfPlays(int amountOfPlays) {
        this.amountOfPlays = amountOfPlays;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(name, playlist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, duration, numberOfSongs, songs, amountOfPlays);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", duration=" + duration +
                ", numberOfSongs=" + numberOfSongs +
                ", songs=" + songs +
                ", amountOfPlays=" + amountOfPlays +
                '}';
    }
}
