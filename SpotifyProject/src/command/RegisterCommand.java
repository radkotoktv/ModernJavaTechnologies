package command;

import file.writer.UserWriter;
import hash.PasswordHasher;
import user.User;

import java.util.List;

import static communication.ConnectConstants.USERS_PATH;
import static communication.ResponseConstants.SUCCESSFUL_REGISTRATION;
import static communication.ResponseConstants.UNSUCCESSFUL_REGISTRATION;

public class RegisterCommand extends Command {
    private final String password;
    private final String email;

    public RegisterCommand(String[] args) {
        super(args);
        password = args[0];
        email = args[1];
    }

    public RegisterCommand(String[] args, List<User> users) {
        super(args, users, List.of(), List.of());
        password = args[0];
        email = args[1];
    }

    @Override
    public String execute() {
        boolean alreadyExists = users.stream()
                                    .anyMatch(p -> p.email().equals(email));
        if (alreadyExists) {
            return UNSUCCESSFUL_REGISTRATION;
        }

        String hashedPassword = PasswordHasher.getInstance().hashPassword(password);
        User newUser = new User(hashedPassword, email);

        users.add(newUser);
        UserWriter.getInstance(USERS_PATH).writeToFile(newUser);
        return SUCCESSFUL_REGISTRATION;
    }
}
