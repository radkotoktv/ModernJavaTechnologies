package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.DISCONNECT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DisconnectCommandTest {
    @Test
    public void testDisconnectCommand() {
        DisconnectCommand disconnectCommand = new DisconnectCommand(null);

        assertEquals(DISCONNECT + System.lineSeparator(), disconnectCommand.execute());
    }
}
