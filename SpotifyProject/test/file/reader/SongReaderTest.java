package file.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import song.Song;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongReaderTest {
    private SongReader songReader;

    @BeforeEach
    void setUp() {
        songReader = new SongReader();
    }

    @Test
    void testReadFromFile() {
        ArrayList<Song> songs = songReader.readFromFile();

        assertNotNull(songs, "Songs should not be null");
        assertEquals(3, songs.size());
        assertEquals("Sign-of-the-Times", songs.getFirst().title());
    }

    @Test
    void testReadFromFileExists() {
        assertDoesNotThrow(songReader::readFromFile);
    }
}
