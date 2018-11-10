package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.CategoryIconHandle;
import seedu.address.model.budget.CategoryBudget;

import java.text.DecimalFormat;

//@author Snookerballs
public class CategoryIconTest extends GuiUnitTest {

    @Test
    public void display() {
        CategoryBudget budget = new CategoryBudget("CAT", "40.00");
        CategoryIcon categoryIcon = new CategoryIcon(budget);
        uiPartRule.setUiPart(categoryIcon);
        CategoryIconHandle categoryIconHandle = new CategoryIconHandle(getChildNode(categoryIcon.getRoot(),
                CategoryIconHandle.CATEGORIES_ICON_ID));
        DecimalFormat df = new DecimalFormat("#.##");
        assertEquals(df.format(categoryIconHandle.getCategoryPercentage()), df.format(budget.getBudgetRatio()));
        assertEquals(categoryIconHandle.getCategoryName(), budget.toString());
    }

}
