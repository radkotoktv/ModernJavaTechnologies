package command;

import file.writer.UserWriter;
import hash.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import user.User;

import java.util.ArrayList;
import java.util.List;

import static communication.ConnectConstants.USERS_PATH;
import static communication.ResponseConstants.SUCCESSFUL_REGISTRATION;
import static communication.ResponseConstants.UNSUCCESSFUL_REGISTRATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterCommandTest {
    List<User> testUsers = new ArrayList<>();
    User user1;
    User user2;

    @BeforeEach
    public void setUp() {
        String hashedPassword1 = PasswordHasher.getInstance().hashPassword("abcd");
        String hashedPassword2 = PasswordHasher.getInstance().hashPassword("efgh");

        user1 = new User(hashedPassword1, "user1@gmail.com");
        user2 = new User(hashedPassword2, "user2@abv.bg");

        testUsers.clear();
        testUsers.add(user1);
        testUsers.add(user2);
    }

    @Test
    public void testRegisterCommandSuccessful() {
        try (MockedStatic<UserWriter> mockedWriter = mockStatic(UserWriter.class)) {
            UserWriter mockUserWriter = mock(UserWriter.class);
            mockedWriter.when(() -> UserWriter.getInstance(USERS_PATH)).thenReturn(mockUserWriter);

            RegisterCommand registerCommand = new RegisterCommand(new String[]{"efgh", "user3abv.bg"}, testUsers);

            assertEquals(SUCCESSFUL_REGISTRATION, registerCommand.execute());
            assertEquals(3, testUsers.size());
            verify(mockUserWriter, times(1)).writeToFile(any(User.class));
        }
    }

    @Test
    public void testRegisterCommandUserAlreadyExists() {
        try (MockedStatic<UserWriter> mockedWriter = mockStatic(UserWriter.class)) {
            UserWriter mockUserWriter = mock(UserWriter.class);
            mockedWriter.when(() -> UserWriter.getInstance(USERS_PATH)).thenReturn(mockUserWriter);

            RegisterCommand registerCommand = new RegisterCommand(new String[]{"efgh", "user2@abv.bg"}, testUsers);

            assertEquals(UNSUCCESSFUL_REGISTRATION, registerCommand.execute());
            assertEquals(2, testUsers.size());
            verify(mockUserWriter, times(0)).writeToFile(any(User.class));
        }
    }
}
