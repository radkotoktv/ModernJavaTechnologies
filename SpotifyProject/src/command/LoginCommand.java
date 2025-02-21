package command;

import hash.PasswordHasher;
import user.User;

import static communication.ResponseConstants.SUCCESSFUL_LOGIN;
import static communication.ResponseConstants.NOT_FOUND_USER;

public class LoginCommand extends Command {
    private final String password;
    private final String email;

    public LoginCommand(String[] args) {
        super(args);
        password = args[0];
        email = args[1];
    }

    @Override
    public String execute() {
        User existingUser = users.stream()
                .filter(u -> u.email().equals(email))
                .findFirst()
                .orElse(null);

        if (existingUser == null) {
            return NOT_FOUND_USER;
        }
        boolean correctPassword = PasswordHasher.getInstance().verifyPassword(password, existingUser.password());

        return !correctPassword ? NOT_FOUND_USER : SUCCESSFUL_LOGIN;
    }
}
