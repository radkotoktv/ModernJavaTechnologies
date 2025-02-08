package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User("password123", "user1@example.com");
        user2 = new User("password123", "user1@example.com");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("password123", user1.password());
        assertEquals("user1@example.com", user1.email());
    }

    @Test
    public void testEquals() {
        assertTrue(user1.equals(user1));
    }

    @Test
    public void testHashCodeEqualObjects() {
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "User{password='password123', email='user1@example.com'}";
        assertEquals(expectedString, user1.toString());
    }
}
