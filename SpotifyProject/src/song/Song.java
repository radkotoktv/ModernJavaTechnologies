package song;

import java.util.Objects;

public class Song {
    private final String title;
    private final String artist;
    private final int duration;
    private int numberOfPlays;
    private final String fileName;

    public Song(String title, String artist, int duration, int numberOfPlays, String fileName) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.numberOfPlays = numberOfPlays;
        this.fileName = fileName;
    }

    public String title() {
        return title;
    }

    public String artist() {
        return artist;
    }

    public int duration() {
        return duration;
    }

    public int numberOfPlays() {
        return numberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

    public String fileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration &&
                numberOfPlays == song.numberOfPlays &&
                Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(fileName, song.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, duration, numberOfPlays, fileName);
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration=" + duration +
                ", numberOfPlays=" + numberOfPlays +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
