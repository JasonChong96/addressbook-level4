package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenses.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.Password;
import seedu.address.model.user.PasswordTest;
import seedu.address.testutil.TypicalExpenses;

//@@author JasonChong96
public class SetPasswordCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullNewPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetPasswordCommand(null, null);
    }

    @Test
    public void constructor_nullOldPassword_assertNoNullPointerException() {
        new SetPasswordCommand(null, PasswordTest.VALID_PASSWORD);
    }

    @Test
    public void execute_noOldPassword_assertSetPasswordSuccessful() throws Exception {
        CommandResult commandResult = new LoginCommand(TypicalExpenses.SAMPLE_USERNAME, null)
                .execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_LOGIN_SUCCESS, TypicalExpenses.SAMPLE_USERNAME.toString()),
                commandResult.feedbackToUser);
        assertTrue(model.hasSelectedUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        commandResult = new SetPasswordCommand(null, PasswordTest.VALID_PASSWORD)
                .execute(model, commandHistory);
        assertEquals(model.getAddressBook().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_SET_PASSWORD_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_oldPasswordInvalid_assertSetPasswordFailure() throws Exception {
        CommandResult commandResult = new LoginCommand(TypicalExpenses.SAMPLE_USERNAME, null)
                .execute(model, commandHistory);
        assertEquals(String.format(LoginCommand.MESSAGE_LOGIN_SUCCESS, TypicalExpenses.SAMPLE_USERNAME.toString()),
                commandResult.feedbackToUser);
        commandResult = new SetPasswordCommand(null, PasswordTest.VALID_PASSWORD)
                .execute(model, commandHistory);
        assertEquals(model.getAddressBook().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_SET_PASSWORD_SUCCESS, commandResult.feedbackToUser);
        commandResult = new SetPasswordCommand(new Password(PasswordTest.INVALID_PASSWORD_STRING_SHORT, false),
                PasswordTest.VALID_PASSWORD).execute(model, commandHistory);
        assertEquals(model.getAddressBook().getPassword().get(), PasswordTest.VALID_PASSWORD);
        assertEquals(SetPasswordCommand.MESSAGE_INCORRECT_PASSWORD, commandResult.feedbackToUser);
    }

    @Test
    public void testEquals() {
        assertEquals(new SetPasswordCommand(PasswordTest.VALID_PASSWORD, PasswordTest.VALID_PASSWORD),
                new SetPasswordCommand(PasswordTest.VALID_PASSWORD, PasswordTest.VALID_PASSWORD));
        assertNotEquals(new SetPasswordCommand(PasswordTest.VALID_PASSWORD, PasswordTest.VALID_PASSWORD),
                new SetPasswordCommand(PasswordTest.VALID_PASSWORD,
                        new Password(PasswordTest.VALID_PASSWORD_STRING.toUpperCase(), true)));
    }
}
