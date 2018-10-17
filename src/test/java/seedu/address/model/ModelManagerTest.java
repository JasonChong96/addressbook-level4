package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;
import static seedu.address.testutil.TypicalExpenses.ICECREAM;
import static seedu.address.testutil.TypicalExpenses.SCHOOLFEE;

import java.nio.file.Paths;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.StatsCommand.StatsMode;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ModelUtil;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = (ModelManager) ModelUtil.modelWithTestUser();
    private ModelManager modelManagerLoggedOut = new ModelManager();

    public ModelManagerTest() throws UserAlreadyExistsException, NonExistentUserException, NoUserSelectedException {
    }

    @Test
    public void checkBudgetRestart_noFrequency_doesNotResetSpending() throws NoUserSelectedException {
        double previousExpenses = modelManager.getMaximumBudget().getCurrentExpenses();
        modelManager.checkBudgetRestart();
        assertTrue(modelManager.getAddressBook().getMaximumBudget().getCurrentExpenses() == previousExpenses);
    }

    @Test
    public void checkBudgetRestart_frequency_doesNotResetSpendingIfNotNextRecurrence() throws NoUserSelectedException {
        double previousExpenses = modelManager.getMaximumBudget().getCurrentExpenses();
        modelManager.setRecurrenceFrequency(Integer.MAX_VALUE);
        modelManager.checkBudgetRestart();
        assertTrue(modelManager.getAddressBook().getMaximumBudget().getCurrentExpenses() == previousExpenses);
    }

    @Test
    public void checkBudgetRestart_frequency_resetSpendingIfNextRecurrence() throws NoUserSelectedException {
        modelManager.setRecurrenceFrequency(0);
        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted. Skipping test.");
            return;
        }
        modelManager.checkBudgetRestart();
        assertTrue(modelManager.getAddressBook().getMaximumBudget().getCurrentExpenses() == 0);
    }


    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() throws NoUserSelectedException {
        thrown.expect(NullPointerException.class);
        modelManager.hasExpense(null);
    }

    @Test
    public void hasExpense_expenseNotInAddressBook_returnsFalse() throws NoUserSelectedException {
        assertFalse(modelManager.hasExpense(SCHOOLFEE));
    }

    @Test
    public void hasExpense_expenseInAddressBook_returnsTrue() throws NoUserSelectedException {
        modelManager.addExpense(SCHOOLFEE);
        assertTrue(modelManager.hasExpense(SCHOOLFEE));
    }

    @Test
    public void getFilteredExpenseList_modifyList_throwsUnsupportedOperationException() throws NoUserSelectedException {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredExpenseList().remove(0);
    }

    @Test
    public void getAddressBook_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.getAddressBook();
    }

    @Test
    public void indicateAddressBookChanged_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.indicateAddressBookChanged();
    }

    @Test
    public void hasExpense_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.hasExpense(SCHOOLFEE);
    }

    @Test
    public void getFilteredExpenseList_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.getFilteredExpenseList();
    }

    @Test
    public void updateFilteredExpenseList_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.updateFilteredExpenseList(unused -> true);
    }

    @Test
    public void canUndoAddressBook_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.canUndoAddressBook();
    }

    @Test
    public void commitAddressBook_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.commitAddressBook();
    }

    @Test
    public void getExpenseStats_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.getExpenseStats();
    }

    @Test
    public void updateExpenseStats_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.updateExpenseStats(unused -> true);
    }


    @Test
    public void getExpenseStatsReturnsCorrectStatsMode() {
        modelManager.updateStatsMode(StatsMode.DAY);
        assertTrue(modelManager.getStatsMode() == StatsMode.DAY);
        modelManager.updateStatsMode(StatsMode.MONTH);
        assertTrue(modelManager.getStatsMode() == StatsMode.MONTH);
    }

    @Test
    public void indicateUserLoggedIn_noUserSelected_throwsNoUserSelectedException() throws Exception {
        thrown.expect(NoUserSelectedException.class);
        modelManagerLoggedOut.indicateUserLoggedIn();
    }

    @Test
    public void equals() throws NoUserSelectedException {
        AddressBook addressBook = new AddressBookBuilder().withExpense(SCHOOLFEE).withExpense(ICECREAM).build();
        AddressBook differentAddressBook = new AddressBook(ModelUtil.TEST_USERNAME, Optional.empty());
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        ArgumentMultimap keywordsMap = ArgumentTokenizer.tokenize(" n/"
                + SCHOOLFEE.getName().expenseName, PREFIX_NAME);
        modelManager.updateFilteredExpenseList(new ExpenseContainsKeywordsPredicate(keywordsMap));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookDirPath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
