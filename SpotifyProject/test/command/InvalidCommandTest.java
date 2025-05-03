package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.INVALID_COMMAND;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidCommandTest {
    @Test
    public void testInvalidCommand() {
        InvalidCommand invalidCommand = new InvalidCommand(null);

        assertEquals(INVALID_COMMAND, invalidCommand.execute());
    }
}
