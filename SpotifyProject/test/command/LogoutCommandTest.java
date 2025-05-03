package command;

import org.junit.jupiter.api.Test;

import static communication.ResponseConstants.SUCCESSFUL_LOGOUT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutCommandTest {
    @Test
    public void testLogoutCommand() {
        LogoutCommand logoutCommand = new LogoutCommand(null);

        assertEquals(SUCCESSFUL_LOGOUT, logoutCommand.execute());
    }
}
