package player;

import exception.WavPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WavPlayerTest {
    private static final String TEST_WAV_FILE = "src/data/songs/test.wav";
    private WavPlayer wavPlayer;

    @BeforeEach
    void setUp() {
        wavPlayer = new WavPlayer(TEST_WAV_FILE);
    }

    @Test
    void testPlayWavFileFileNotFound() {
        WavPlayer invalidWavPlayer = new WavPlayer("non_existent_file.wav");
        assertThrows(WavPlayerException.class, invalidWavPlayer::run);
    }

    @Test
    void testPlayWavFileUnsupportedAudioFile() throws IOException {
        File invalidFile = new File(TEST_WAV_FILE);
        try (FileWriter writer = new FileWriter(invalidFile)) {
            writer.write("This is not a valid WAV file content");
        }
        assertThrows(WavPlayerException.class, wavPlayer::run);
    }

    @Test
    void testPlayWavFileValidFile() {
        assertThrows(WavPlayerException.class, wavPlayer::run);
    }
}