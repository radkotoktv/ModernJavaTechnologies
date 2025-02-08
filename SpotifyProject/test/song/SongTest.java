package song;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {

    private Song song1;
    private Song song2;
    private Song song3;

    @BeforeEach
    public void setUp() {
        song1 = new Song("Song1", "Artist1", 300, 10, "song1.mp3");
        song2 = new Song("Song1", "Artist1", 300, 10, "song1.mp3");
        song3 = new Song("Song3", "Artist3", 250, 5, "song3.mp3");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Song1", song1.title());
        assertEquals("Artist1", song1.artist());
        assertEquals(300, song1.duration());
        assertEquals(10, song1.numberOfPlays());
        assertEquals("song1.mp3", song1.fileName());
    }

    @Test
    public void testEquals() {
        assertTrue(song1.equals(song1));
    }

    @Test
    public void testHashCodeEqualObjects() {
        assertEquals(song1.hashCode(), song2.hashCode());
    }

    @Test
    public void testHashCodeDifferentObjects() {
        assertNotEquals(song1.hashCode(), song3.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Song{title='Song1', artist='Artist1', duration=300, numberOfPlays=10, fileName='song1.mp3'}";
        assertEquals(expectedString, song1.toString());
    }
}
