package hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {

    @Test
    void testSingletonInstanceIsSame() {
        PasswordHasher instance1 = PasswordHasher.getInstance();
        PasswordHasher instance2 = PasswordHasher.getInstance();

        assertSame(instance1, instance2, "Instances should be the same (singleton)");
    }

    @Test
    void testHashPasswordReturnsNonNull() {
        PasswordHasher hasher = PasswordHasher.getInstance();
        String hash = hasher.hashPassword("mySecretPassword");

        assertNotNull(hash, "Hashed password should not be null");
    }

    @Test
    void testHashingProducesDifferentHashesForDifferentInputs() {
        PasswordHasher hasher = PasswordHasher.getInstance();
        String hash1 = hasher.hashPassword("password1");
        String hash2 = hasher.hashPassword("password2");

        assertNotEquals(hash1, hash2, "Different passwords should have different hashes");
    }

    @Test
    void testVerifyPasswordSuccess() {
        PasswordHasher hasher = PasswordHasher.getInstance();
        String password = "correctPassword";
        String hash = hasher.hashPassword(password);

        assertTrue(hasher.verifyPassword(password, hash), "Password verification should succeed for correct password");
    }

    @Test
    void testVerifyPasswordFailure() {
        PasswordHasher hasher = PasswordHasher.getInstance();
        String password = "correctPassword";
        String wrongPassword = "wrongPassword";
        String hash = hasher.hashPassword(password);

        assertFalse(hasher.verifyPassword(wrongPassword, hash), "Password verification should fail for incorrect password");
    }
}
