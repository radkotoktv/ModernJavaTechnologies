package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.PRINT_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCommandTest {
    @Test
    public void testUserCommand(){
        Command userCommand = new UserCommand(null);
        assertEquals(PRINT_USER, userCommand.execute());
    }
}
