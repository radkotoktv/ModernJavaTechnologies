package song;

import java.util.Objects;

public record Song(String title, String artist, int duration, int numberOfPlays, String fileName) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return duration == song.duration && numberOfPlays == song.numberOfPlays && Objects.equals(title, song.title) && Objects.equals(artist, song.artist) && Objects.equals(fileName, song.fileName);
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
