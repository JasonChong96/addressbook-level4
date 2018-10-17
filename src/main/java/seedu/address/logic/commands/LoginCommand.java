package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

//@@author JasonChong96
/**
 * Logs in a user in the model
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "li";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs in to the user with the given USERNAME and PASSWORD. The password\n"
            + "Parameters: u/USERNAME [p/PASSWORD]\n"
            + "Example: " + COMMAND_WORD + " u/examplename\n"
            + COMMAND_WORD + "u/john p/password123";

    public static final String MESSAGE_LOGIN_SUCCESS = "Logged in as %1$s";
    public static final String MESSAGE_INCORRECT_PASSWORD = "Incorrect password";

    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        requireNonNull(username);
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NonExistentUserException {
        requireNonNull(model);
        if (model.loadUserData(this.username, this.password)) {
            return new CommandResult(String.format(MESSAGE_LOGIN_SUCCESS, this.username.toString()));
        } else {
            return new CommandResult(MESSAGE_INCORRECT_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && username.equals(((LoginCommand) other).username)); // state check
    }
}
