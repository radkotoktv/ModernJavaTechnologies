package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.HELP_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {
    @Test
    public void testHelpCommand() {
        HelpCommand helpCommand = new HelpCommand(null);

        assertEquals(HELP_TEXT, helpCommand.execute());
    }
}
