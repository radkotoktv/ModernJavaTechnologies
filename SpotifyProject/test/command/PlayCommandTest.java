package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import song.Song;

import java.util.ArrayList;
import java.util.List;

import static communication.ResponseConstants.SONG_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayCommandTest {
    List<Song> testSongs = new ArrayList<>();
    Song song1 = new Song("a", "Harry-Styles", 340, 5, "sign_of_the_times.wav");
    Song song2 = new Song("b", "Harry-Styles", 174, 2, "watermelon_sugar.wav");
    Song song3 = new Song("c", "Ed-Sheeran", 263, 4, "perfect.wav");

    @BeforeEach
    public void setUp() {
        testSongs.clear();

        testSongs.add(song1);
        testSongs.add(song2);
        testSongs.add(song3);
    }

    @Test
    public void testPlayCommandNoSongName() {
        PlayCommand playCommand = new PlayCommand(new String[]{null}, null);

        assertEquals(SONG_NOT_FOUND, playCommand.execute());
    }

    @Test
    public void testPlayCommandSongNotFound() {
        PlayCommand playCommand = new PlayCommand(new String[]{"d"}, testSongs);

        assertEquals(SONG_NOT_FOUND, playCommand.execute());
    }

    @Test
    public void testPlayCommandSuccessful() {
        PlayCommand playCommand = new PlayCommand(new String[]{"b"}, testSongs);

        assertEquals(song2.fileName(), playCommand.execute());
    }
}
