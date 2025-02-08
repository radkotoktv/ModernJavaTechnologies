package file.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import playlist.Playlist;

import java.io.IOException;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

class PlaylistReaderTest {
    private PlaylistReader playlistReader;

    @BeforeEach
    void setUp() {
        playlistReader = new PlaylistReader();
    }

    @Test
    void testReadFromFile() {
        ArrayList<Playlist> playlists = playlistReader.readFromFile();

        assertNotNull(playlists, "Playlists should not be null");
        assertEquals(2, playlists.size());

        Playlist firstPlaylist = playlists.getFirst();
        assertEquals("Summer-Vibes", firstPlaylist.name());
        assertEquals("John-Doe", firstPlaylist.owner());
        assertEquals(2, firstPlaylist.numberOfSongs());
        assertEquals(777, firstPlaylist.duration());
        assertEquals(0, firstPlaylist.amountOfPlays());
    }

    @Test
    void testReadFromFileExists() {
        assertDoesNotThrow(playlistReader::readFromFile);
    }
}
