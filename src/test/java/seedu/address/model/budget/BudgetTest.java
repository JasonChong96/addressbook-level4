package seedu.address.model.budget;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class BudgetTest {
    public static final String VALID_BUDGET = "2.00";
    public static final String INVALID_BUDGET = "A";
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Budget(BudgetTest.INVALID_BUDGET));
    }

    @Test
    public void isValidBudget() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        // invalid costs
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("200")); // number only

        // valid costs
        assertTrue(Budget.isValidBudget("255.00"));
        assertTrue(Budget.isValidBudget("1.00")); // one dollar
        assertTrue(Budget.isValidBudget("231231232131231.00")); // high ses budget
    }

    @Test
    public void equals() {
        assertTrue(new Budget(0, 0).equals(new Budget("0.00")));
        assertTrue(new Budget(0, 0).equals(new Budget(0, 0, null, 50000)));
    }
}
