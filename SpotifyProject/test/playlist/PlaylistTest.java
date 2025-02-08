package playlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import song.Song;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private Playlist playlist;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        ArrayList<Song> songs = new ArrayList<>();
        song1 = new Song("Song1", "Artist1", 200, 2, "song1.wav");
        song2 = new Song("Song2", "Artist2", 250, 3, "song2.wav");
        songs.add(song1);
        playlist = new Playlist("My Playlist", "Owner", 200, 1, songs, 5);
    }

    @Test
    public void testAddSongNewSong() {
        playlist.addSong(song2);

        assertEquals(2, playlist.numberOfSongs());
        assertEquals(450, playlist.duration());
        assertTrue(playlist.songs().contains(song2));
    }

    @Test
    public void testAddSongAlreadyExists() {
        playlist.addSong(song1);

        assertEquals(1, playlist.numberOfSongs());
        assertEquals(200, playlist.duration());
    }

    @Test
    public void testEquals() {
        ArrayList<Song> sameSongs = new ArrayList<>();
        sameSongs.add(song1);
        Playlist samePlaylist = new Playlist("My Playlist", "Owner", 200, 1, sameSongs, 5);

        assertTrue(playlist.equals(samePlaylist));
    }

    @Test
    public void testEqualsDifferentOwner() {
        ArrayList<Song> sameSongs = new ArrayList<>();
        sameSongs.add(song1);
        Playlist differentOwnerPlaylist = new Playlist("My Playlist", "Different Owner", 200, 1, sameSongs, 5);

        assertFalse(playlist.equals(differentOwnerPlaylist));
    }
}
