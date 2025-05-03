package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.STOP_SONG;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StopCommandTest {
    @Test
    public void testStopCommand(){
        Command stopCommand = new StopCommand(null);
        assertEquals(STOP_SONG, stopCommand.execute());
    }
}
