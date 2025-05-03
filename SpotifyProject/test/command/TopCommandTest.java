package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import song.Song;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TopCommandTest {
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
    public void testTopCommandCorrect() {
        TopCommand topCommand = new TopCommand(new String[]{"2"}, testSongs);

        assertEquals("Top 2 songs:\n" + song1.toString() + "\n" + song3.toString() + "\n", topCommand.execute());
    }

    @Test
    public void testTopCommandZero() {
        TopCommand topCommand = new TopCommand(new String[]{"0"}, testSongs);

        assertEquals("Top 0 songs:\n", topCommand.execute());
    }

    @Test
    public void testTopCommandNegative() {
        TopCommand topCommand = new TopCommand(new String[]{"-5"}, testSongs);

        assertThrows(IllegalArgumentException.class, topCommand::execute);
    }
}
