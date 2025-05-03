package command;

import file.writer.PlaylistWriter;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import playlist.Playlist;
import song.Song;

import java.util.ArrayList;
import java.util.List;

import static communication.ConnectConstants.PLAYLISTS_PATH;
import static communication.ResponseConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CreatePlaylistCommandTest {
    Playlist testPlaylist = new Playlist("playlist1", "user1", 150, 0, List.of(), 0);
    Song song1 = new Song("a", "Harry-Styles", 340, 5, "sign_of_the_times.wav");
    Song song2 = new Song("b", "Harry-Styles", 174, 2, "watermelon_sugar.wav");
    List<Playlist> playlistListTest = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        testPlaylist.setSongs(List.of(song1, song2));

        playlistListTest.clear();
        playlistListTest.add(testPlaylist);
    }

    @Test
    public void testCreatePlaylistCommandAlreadyExists() {
        CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand(new String[]{"playlist1", "temp"},
                List.of(testPlaylist));

        assertEquals(PLAYLIST_ALREADY_EXISTS, createPlaylistCommand.execute());
    }

    @Test
    public void testCreatePlaylistCommandSuccessful() {
        try (MockedStatic<PlaylistWriter> mockedWriter = mockStatic(PlaylistWriter.class)) {
            PlaylistWriter mockPlaylistWriter = mock(PlaylistWriter.class);
            mockedWriter.when(() -> PlaylistWriter.getInstance(PLAYLISTS_PATH)).thenReturn(mockPlaylistWriter);

            CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand(new String[]{"newName", "user3abv.bg"}, playlistListTest);

            assertEquals(SUCCESSFUL_PLAYLIST_CREATION, createPlaylistCommand.execute());
            assertEquals(2, playlistListTest.size());
            verify(mockPlaylistWriter, times(1)).writeToFile(any(Playlist.class));
        }
    }

}
