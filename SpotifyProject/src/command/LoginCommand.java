package command;

import user.User;

import static communication.ResponseConstants.SUCCESSFUL_LOGIN;

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
        User toLogin = new User(password, email);
        if (!users.contains(toLogin)) {
            return "User not found!";
        }
        return SUCCESSFUL_LOGIN;
    }
}
