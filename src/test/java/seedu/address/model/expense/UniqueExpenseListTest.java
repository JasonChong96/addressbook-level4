package seedu.address.model.expense;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalExpenses.BOB;
import static seedu.address.testutil.TypicalExpenses.SCHOOLFEE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.ExpenseBuilder;

public class UniqueExpenseListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueExpenseList uniqueExpenseList = new UniqueExpenseList();

    @Test
    public void contains_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.contains(null);
    }

    @Test
    public void contains_expenseNotInList_returnsFalse() {
        assertFalse(uniqueExpenseList.contains(SCHOOLFEE));
    }

    @Test
    public void contains_expenseInList_returnsTrue() {
        uniqueExpenseList.add(SCHOOLFEE);
        assertTrue(uniqueExpenseList.contains(SCHOOLFEE));
    }

    @Test
    public void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExpenseList.add(SCHOOLFEE);
        Expense editedAlice = new ExpenseBuilder(SCHOOLFEE).withCost(VALID_COST_IPHONE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueExpenseList.contains(editedAlice));
    }

    @Test
    public void add_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.add(null);
    }

    @Test
    public void add_duplicateExpense_throwsDuplicateExpenseException() {
        uniqueExpenseList.add(SCHOOLFEE);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.add(SCHOOLFEE);
    }

    @Test
    public void setExpense_nullTargetExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpense(null, SCHOOLFEE);
    }

    @Test
    public void setExpense_nullEditedExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpense(SCHOOLFEE, null);
    }

    @Test
    public void setExpense_targetExpenseNotInList_throwsExpenseNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        uniqueExpenseList.setExpense(SCHOOLFEE, SCHOOLFEE);
    }

    @Test
    public void setExpense_editedExpenseIsSameExpense_success() {
        uniqueExpenseList.add(SCHOOLFEE);
        uniqueExpenseList.setExpense(SCHOOLFEE, SCHOOLFEE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(SCHOOLFEE);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpense_editedExpenseHasSameIdentity_success() {
        uniqueExpenseList.add(SCHOOLFEE);
        Expense editedAlice = new ExpenseBuilder(SCHOOLFEE).withCost(VALID_COST_IPHONE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueExpenseList.setExpense(SCHOOLFEE, editedAlice);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(editedAlice);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpense_editedExpenseHasDifferentIdentity_success() {
        uniqueExpenseList.add(SCHOOLFEE);
        uniqueExpenseList.setExpense(SCHOOLFEE, BOB);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpense_editedExpenseHasNonUniqueIdentity_throwsDuplicateExpenseException() {
        uniqueExpenseList.add(SCHOOLFEE);
        uniqueExpenseList.add(BOB);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.setExpense(SCHOOLFEE, BOB);
    }

    @Test
    public void remove_nullExpense_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.remove(null);
    }

    @Test
    public void remove_expenseDoesNotExist_throwsExpenseNotFoundException() {
        thrown.expect(ExpenseNotFoundException.class);
        uniqueExpenseList.remove(SCHOOLFEE);
    }

    @Test
    public void remove_existingExpense_removesExpense() {
        uniqueExpenseList.add(SCHOOLFEE);
        uniqueExpenseList.remove(SCHOOLFEE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_nullUniqueExpenseList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpenses((UniqueExpenseList) null);
    }

    @Test
    public void setExpenses_uniqueExpenseList_replacesOwnListWithProvidedUniqueExpenseList() {
        uniqueExpenseList.add(SCHOOLFEE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        uniqueExpenseList.setExpenses(expectedUniqueExpenseList);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueExpenseList.setExpenses((List<Expense>) null);
    }

    @Test
    public void setExpenses_list_replacesOwnListWithProvidedList() {
        uniqueExpenseList.add(SCHOOLFEE);
        List<Expense> expenseList = Collections.singletonList(BOB);
        uniqueExpenseList.setExpenses(expenseList);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setExpenses_listWithDuplicateExpenses_throwsDuplicateExpenseException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(SCHOOLFEE, SCHOOLFEE);
        thrown.expect(DuplicateExpenseException.class);
        uniqueExpenseList.setExpenses(listWithDuplicateExpenses);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueExpenseList.asUnmodifiableObservableList().remove(0);
    }
}
