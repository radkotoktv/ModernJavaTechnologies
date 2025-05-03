package command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import song.Song;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SearchCommandTest {
    static List<Song> testSongs = new ArrayList<>();
    static Song song1 = new Song("a", "Harry-Styles", 340, 5, "sign_of_the_times.wav");
    static Song song2 = new Song("b", "Harry-Styles", 174, 2, "watermelon_sugar.wav");
    static Song song3 = new Song("c", "Ed-Sheeran", 263, 4, "perfect.wav");

    @BeforeAll
    public static void setUp() {
        testSongs.add(song1);
        testSongs.add(song2);
        testSongs.add(song3);
    }

    @Test
    public void testSearchCommandSuccessfulOneSong() {
        SearchCommand searchCommand = new SearchCommand(new String[]{"Sheeran"}, testSongs);

        assertEquals("Songs containing Sheeran :\n" + song3.toString() + "\n", searchCommand.execute());
    }

    @Test
    public void testSearchCommandFail() {
        SearchCommand searchCommand = new SearchCommand(new String[]{"Eminem"}, testSongs);

        assertEquals("Songs containing Eminem :\n", searchCommand.execute());
    }

    @Test
    public void testSearchCommandSuccessfulMultipleSongs() {
        SearchCommand searchCommand = new SearchCommand(new String[]{"Harry"}, testSongs);

        assertEquals("Songs containing Harry :\n" +
                song1.toString() + "\n" +
                song2.toString() + "\n",
                searchCommand.execute());
    }

}
