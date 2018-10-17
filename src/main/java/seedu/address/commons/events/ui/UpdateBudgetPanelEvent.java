package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.budget.Budget;

//@@author snookerballs

/**
 * An event requesting an update in budget display.
 */
public class UpdateBudgetPanelEvent extends BaseEvent {

    public final Budget budget;

    public UpdateBudgetPanelEvent(Budget budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
