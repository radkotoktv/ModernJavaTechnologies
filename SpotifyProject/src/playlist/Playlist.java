package playlist;

import song.Song;

import java.util.ArrayList;
import java.util.Objects;

import static constants.Constant.PLAYLIST_WRITER;

public class Playlist {
    private final String name;
    private final String owner;
    private int duration;
    private int numberOfSongs;
    private ArrayList<Song> songs;
    private int amountOfPlays;

    public Playlist(String name, String owner, int duration, int numberOfSongs, ArrayList<Song> songs, int amountOfPlays) {
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
        this.numberOfSongs++;
        this.duration += newSong.duration();
        PLAYLIST_WRITER.writeToFile(this);
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

    public ArrayList<Song> songs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
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
        return duration == playlist.duration && numberOfSongs == playlist.numberOfSongs && amountOfPlays == playlist.amountOfPlays && Objects.equals(name, playlist.name) && Objects.equals(owner, playlist.owner) && Objects.equals(songs, playlist.songs);
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
