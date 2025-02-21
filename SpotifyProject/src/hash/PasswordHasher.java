package hash;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

import static hash.HashingConstants.LOG_ROUNDS;

public class PasswordHasher {
    private static volatile PasswordHasher instance;
    private final BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, LOG_ROUNDS);

    private PasswordHasher() {
    }

    public static PasswordHasher getInstance() {
        PasswordHasher result = instance;
        if (result != null) {
            return result;
        }

        synchronized (PasswordHasher.class) {
            if (instance == null) {
                instance = new PasswordHasher();
            }
            return instance;
        }
    }

    public String hashPassword(String password) {
        Hash hash = Password.hash(password)
                .addPepper("shared-secret")
                .with(bcrypt);

        return hash.getResult();
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return Password.check(password, hashedPassword)
                .addPepper("shared-secret")
                .with(bcrypt);
    }
}
