package command;

import hash.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.util.ArrayList;
import java.util.List;

import static communication.ResponseConstants.NOT_FOUND_USER;
import static communication.ResponseConstants.SUCCESSFUL_LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginCommandTest {
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
    public void testLoginCommandUserNotFound() {
        LoginCommand loginCommand = new LoginCommand(new String[]{"aaa", "user3@hotmail.com"}, testUsers);

        assertEquals(NOT_FOUND_USER, loginCommand.execute());
    }

    @Test
    public void testLoginCommandIncorrectPassword() {
        LoginCommand loginCommand = new LoginCommand(new String[]{"abcde", "user1@gmail.com"}, testUsers);

        assertEquals(NOT_FOUND_USER, loginCommand.execute());
    }

    @Test
    public void testLoginCommandSuccessful() {
        LoginCommand loginCommand = new LoginCommand(new String[]{"efgh", "user2@abv.bg"}, testUsers);

        assertEquals(SUCCESSFUL_LOGIN, loginCommand.execute());
    }
}
