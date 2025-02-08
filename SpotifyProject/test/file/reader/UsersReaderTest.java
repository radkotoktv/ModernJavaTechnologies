package file.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UsersReaderTest {
    private UsersReader usersReader;

    @BeforeEach
    void setUp() {
        usersReader = new UsersReader();
    }

    @Test
    void testReadFromFile() {
        ArrayList<User> users = usersReader.readFromFile();

        assertNotNull(users, "Users should not be null");
        assertEquals(3, users.size());

        User firstUser = new User("parola123", "Ivan@gmail.com");
        assertEquals(firstUser, users.getFirst());

        User secondUser = new User("parolatae123", "Maria@gmail.com");
        assertEquals(secondUser, users.get(1));

        User thirdUser = new User("MJT2025", "Stoyan@hotmail.com");
        assertEquals(thirdUser, users.get(2));
    }

    @Test
    void testReadFromFileExists() {
        assertDoesNotThrow(usersReader::readFromFile);
    }
}
