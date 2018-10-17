package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalAddressBook;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.testutil.AddressBookBuilder;


public class SetRecurringBudgetCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private long newRecurrenceFrequency = 123456;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void execute_setRecurrence_successful() throws NoUserSelectedException {
        AddressBook emptyBook = new AddressBookBuilder().build();
        model = new ModelManager(emptyBook, new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setRecurrenceFrequency(this.newRecurrenceFrequency);
        expectedModel.commitAddressBook();
        SetRecurringBudgetCommand setRecurringBudgetCommand = new SetRecurringBudgetCommand(newRecurrenceFrequency);
        String expectedMessage = String.format(SetRecurringBudgetCommand.MESSAGE_SUCCESS, this.newRecurrenceFrequency);
        assertCommandSuccess(setRecurringBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
        assertNotNull(expectedModel.getMaximumBudget().getNextRecurrence());
        assertTrue(this.newRecurrenceFrequency
            == expectedModel.getAddressBook().getMaximumBudget().getNumberOfSecondsToRecurAgain());

    }
    @Test
    public void execute_updateRecurrence_successful() throws NoUserSelectedException {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        long initialRecurrenceFrequency =
            expectedModel.getAddressBook().getMaximumBudget().getNumberOfSecondsToRecurAgain();
        expectedModel.setRecurrenceFrequency(this.newRecurrenceFrequency);
        expectedModel.commitAddressBook();
        String expectedMessage = String.format(SetRecurringBudgetCommand.MESSAGE_SUCCESS, this.newRecurrenceFrequency);
        SetRecurringBudgetCommand setRecurringBudgetCommand = new SetRecurringBudgetCommand(newRecurrenceFrequency);
        assertCommandSuccess(setRecurringBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
        assertFalse(initialRecurrenceFrequency
            == expectedModel.getAddressBook().getMaximumBudget().getNumberOfSecondsToRecurAgain());
    }

    @Test
    public void equals() {
        assertTrue(new SetRecurringBudgetCommand(1)
            .equals(new SetRecurringBudgetCommand(1)));
    }

}
