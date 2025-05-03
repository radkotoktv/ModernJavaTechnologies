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

public class AddSongToPlaylistCommandTest {
    List<Song> testSongs = new ArrayList<>();
    Song song1 = new Song("a", "Harry-Styles", 340, 5, "sign_of_the_times.wav");
    Song song2 = new Song("b", "Harry-Styles", 174, 2, "watermelon_sugar.wav");
    Song song3 = new Song("c", "Ed-Sheeran", 263, 4, "perfect.wav");
    Playlist testPlaylist = new Playlist("playlist1", "user1", 150, 0, new ArrayList<>(List.of(song3)), 0);
    List<Playlist> testPlaylists = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        testSongs.clear();
        testSongs.add(song1);
        testSongs.add(song2);
        testSongs.add(song3);

        testPlaylists.clear();
        testPlaylists.add(testPlaylist);
    }

    @Test
    public void testAddSongToPlaylistCommandPlaylistNotExist() {
        AddSongToPlaylistCommand addSongToPlaylistCommand = new AddSongToPlaylistCommand(
                new String[]{"wrongName", "songName", "ownerEmail"},
                testSongs,
                testPlaylists);

        assertEquals(PLAYLIST_NOT_FOUND, addSongToPlaylistCommand.execute());
    }

    @Test
    public void testAddSongToPlaylistCommandNoPermission() {
        AddSongToPlaylistCommand addSongToPlaylistCommand = new AddSongToPlaylistCommand(
                new String[]{"playlist1", "songName", "ownerEmail"},
                testSongs,
                testPlaylists);

        assertEquals(NO_PERMISSION, addSongToPlaylistCommand.execute());
    }

    @Test
    public void testAddSongToPlaylistCommandSongNotFound() {
        AddSongToPlaylistCommand addSongToPlaylistCommand = new AddSongToPlaylistCommand(
                new String[]{"playlist1", "songName", "user1"},
                testSongs,
                testPlaylists);

        assertEquals(SONG_NOT_FOUND, addSongToPlaylistCommand.execute());
    }

    @Test
    public void testAddSongToPlaylistCommandSongAlreadyInside() {
        AddSongToPlaylistCommand addSongToPlaylistCommand = new AddSongToPlaylistCommand(
                new String[]{"playlist1", "c", "user1"},
                testSongs,
                testPlaylists);

        assertEquals(SONG_ALREADY_INSIDE, addSongToPlaylistCommand.execute());
    }

    @Test
    public void testAddSongToPlaylistCommandSuccessful() {
        try (MockedStatic<PlaylistWriter> mockedWriter = mockStatic(PlaylistWriter.class)) {
            PlaylistWriter mockPlaylistWriter = mock(PlaylistWriter.class);
            mockedWriter.when(() -> PlaylistWriter.getInstance(PLAYLISTS_PATH)).thenReturn(mockPlaylistWriter);

            AddSongToPlaylistCommand addSongToPlaylistCommand = new AddSongToPlaylistCommand(
                    new String[]{"playlist1", "b", "user1"},
                    testSongs,
                    testPlaylists);
            assertEquals(SUCCESSFUL_SONG_ADDITION, addSongToPlaylistCommand.execute());
            assertEquals(1, testPlaylists.size());
            assertEquals(2, testPlaylist.songs().size());
            verify(mockPlaylistWriter, times(1)).writeToFile(any(Playlist.class));
        }
    }
}
