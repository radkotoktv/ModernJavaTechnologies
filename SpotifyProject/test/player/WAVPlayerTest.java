package player;

import exception.WavPlayerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WAVPlayerTest {
    @Test
    void testConstructorAppendsWavPath() {
        String testFile = "test.wav";
        WavPlayer player = new WavPlayer(testFile);

        try {
            var field = WavPlayer.class.getDeclaredField("fileName");
            field.setAccessible(true);
            String resultPath = (String) field.get(player);
            assertTrue(resultPath.endsWith(testFile), "Filename should end with test file name");
        } catch (ReflectiveOperationException e) {
            fail("Reflection failed");
        }
    }

    @Test
    void testPlayWavFileThrowsWavPlayerExceptionOnInvalidFile() {
        WavPlayer player = new WavPlayer("nonexistent.wav");

        assertThrows(WavPlayerException.class, () -> {
            player.playWavFile("nonexistent_file.wav");
        });
    }
}
