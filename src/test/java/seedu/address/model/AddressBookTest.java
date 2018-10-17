package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalExpenses.SCHOOLFEE;
import static seedu.address.testutil.TypicalExpenses.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.ModelUtil;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook(ModelUtil.TEST_USERNAME, Optional.empty());

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        this.addressBook.resetData(null);
        assertTrue(this.addressBook.getMaximumBudget().getBudgetCap() == 0);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
        assertEquals(newData.getMaximumBudget(), addressBook.getMaximumBudget());
    }

    @Test
    public void resetData_withDuplicateExpenses_throwsDuplicateExpenseException() {
        // Two expenses with the same identity fields
        Expense editedAlice = new ExpenseBuilder(SCHOOLFEE).withCost(VALID_COST_IPHONE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Expense> newExpenses = Arrays.asList(SCHOOLFEE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newExpenses);

        thrown.expect(DuplicateExpenseException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasExpense_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasExpense(null);
    }

    @Test
    public void hasExpense_expenseNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasExpense(SCHOOLFEE));
    }

    @Test
    public void hasExpense_expenseInAddressBook_returnsTrue() {
        addressBook.addExpense(SCHOOLFEE);
        assertTrue(addressBook.hasExpense(SCHOOLFEE));
    }

    @Test
    public void hasExpense_expenseWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addExpense(SCHOOLFEE);
        Expense editedAlice = new ExpenseBuilder(SCHOOLFEE).withCost(VALID_COST_IPHONE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasExpense(editedAlice));
    }

    @Test
    public void getExpenseList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getExpenseList().remove(0);
    }


    /**
     * A stub ReadOnlyAddressBook whose expenses list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

        AddressBookStub(Collection<Expense> expenses) {
            this.expenses.setAll(expenses);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public Budget getMaximumBudget() {
            return new Budget("0.00");
        }

        @Override
        public Username getUsername() {
            return ModelUtil.TEST_USERNAME;
        }

        @Override
        public Optional<Password> getPassword() {
            return Optional.empty();
        }

        @Override
        public boolean isMatchPassword(Password password) {
            return true;
        }
    }



}
