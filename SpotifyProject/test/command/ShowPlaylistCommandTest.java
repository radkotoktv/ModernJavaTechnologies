package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import playlist.Playlist;
import song.Song;

import java.util.List;

import static communication.ResponseConstants.UNSUCCESSFUL_PLAYLIST_SHOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowPlaylistCommandTest {
    Playlist testPlaylist = new Playlist("playlist1", "user1", 150, 0, List.of(), 0);
    Song song1 = new Song("a", "Harry-Styles", 340, 5, "sign_of_the_times.wav");
    Song song2 = new Song("b", "Harry-Styles", 174, 2, "watermelon_sugar.wav");

    @BeforeEach
    public void setUp() {
        testPlaylist.setSongs(List.of(song1, song2));
    }

    @Test
    public void testShowPlaylistCommandSuccessful() {
        ShowPlaylistCommand showPlaylistCommand = new ShowPlaylistCommand(new String[]{"playlist1"}, List.of(testPlaylist));

        assertEquals(testPlaylist.toString(), showPlaylistCommand.execute());
    }

    @Test
    public void testShowPlaylistCommandFail() {
        ShowPlaylistCommand showPlaylistCommand = new ShowPlaylistCommand(new String[]{"playlist2"}, List.of(testPlaylist));

        assertEquals(UNSUCCESSFUL_PLAYLIST_SHOW, showPlaylistCommand.execute());
    }
}
