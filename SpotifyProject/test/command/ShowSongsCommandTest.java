package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import song.Song;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowSongsCommandTest {
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
    public void testShowSongsCommmand() {
        ShowSongsCommand showSongsCommand = new ShowSongsCommand(null, testSongs);

        assertEquals(testSongs.toString(), showSongsCommand.execute());
    }
}
