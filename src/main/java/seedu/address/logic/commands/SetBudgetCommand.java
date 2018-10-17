package seedu.address.logic.commands;
//@author winsonhys
import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.UpdateBudgetPanelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;

/**
 * Sets the budget cap for the expense tracker.
 */

public class SetBudgetCommand extends Command {
    public static final String COMMAND_WORD = "setBudget";
    public static final String COMMAND_ALIAS = "sb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a budget cap for the expense tracker "
        + "Parameters: MONEY (Must be a positive float with 2 decimal places)\n"
        + "Example " + COMMAND_WORD + " 130.00";

    public static final String MESSAGE_SUCCESS = "Budget set to %1$s";
    private final Budget toSet;

    /**
     * Creates an AddCommand to add the specified {@code Budget}
     */

    public SetBudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.toSet = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException {
        requireNonNull(model);

        model.modifyMaximumBudget(this.toSet);
        model.commitAddressBook();
        EventsCenter.getInstance().post(new UpdateBudgetPanelEvent(model.getMaximumBudget()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toSet));
    }

}
