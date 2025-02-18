package command;

import file.writer.UserWriter;
import user.User;

import static communication.ConnectConstants.USERS_PATH;
import static communication.ResponseConstants.SUCCESSFUL_REGISTRATION;

public class RegisterCommand extends Command {
    private final String password;
    private final String email;

    public RegisterCommand(String[] args) {
        super(args);
        password = args[0];
        email = args[1];
    }

    @Override
    public String execute() {
        User newUser = new User(password, email);
        if (users.contains(newUser)) {
            return "User already exists!";
        }
        users.add(newUser);
        UserWriter.getInstance(USERS_PATH).writeToFile(newUser);
        return SUCCESSFUL_REGISTRATION;
    }
}
