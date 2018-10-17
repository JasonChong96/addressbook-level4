package seedu.address.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the expenses list.
     * This list will not contain any duplicate expenses.
     */
    ObservableList<Expense> getExpenseList();
    Budget getMaximumBudget();
    Username getUsername();

    Optional<Password> getPassword();

    /**
     * Checks if the input password matches the password of the current user. If the user has no password, then true
     * is returned.
     * @param password the password to check
     * @return true if the user has no password or if the input password matches his/her password, or else false
     */
    boolean isMatchPassword(Password password);
}
