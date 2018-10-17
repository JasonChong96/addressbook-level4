package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DAYS_OR_MONTHS;

import java.util.Calendar;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowStatsRequestEvent;
import seedu.address.commons.events.ui.SwapLeftPanelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.expense.Expense;

//@@author jonathantjm
/**
 * Opens up the stats window for the user.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String COMMAND_ALIAS = "st";


    public static final String MESSAGE_SUCCESS = "swapped to stats window";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens stats window. "
            + "Parameters: "
            + PREFIX_NUMBER_OF_DAYS_OR_MONTHS + "NUMBER_OF_DAYS_OR_MONTHS "
            + PREFIX_MODE + "FORMAT (either 'm' or 'd')\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NUMBER_OF_DAYS_OR_MONTHS + "7 "
            + PREFIX_MODE + "d";

    public static final String MESSAGE_PARAMETERS_FORMAT = "Command should be in format: \n"
            + COMMAND_WORD + " "
            + PREFIX_NUMBER_OF_DAYS_OR_MONTHS + "NUMBER_OF_DAYS_OR_MONTHS "
            + PREFIX_MODE + "FORMAT (either 'm' or 'd')\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NUMBER_OF_DAYS_OR_MONTHS + "7 "
            + PREFIX_MODE + "d";

    /**
     *  Enum for StatsMode. StatsMode can either be MONTH or DAY
     */
    public enum StatsMode {
        MONTH, DAY
    }

    private int numberOfDaysOrMonths;
    private StatsMode mode;

    public StatsCommand(int numberOfDaysOrMonths, String mode) {
        requireNonNull(numberOfDaysOrMonths, mode);
        checkArgument(isValidMode(mode), MESSAGE_PARAMETERS_FORMAT);
        checkArgument(isValidNumber(numberOfDaysOrMonths), MESSAGE_PARAMETERS_FORMAT);
        this.numberOfDaysOrMonths = numberOfDaysOrMonths;
        if ("d".equals(mode)) {
            this.mode = StatsMode.DAY;
        } else {
            this.mode = StatsMode.MONTH;
        }
    }

    public StatsCommand() {
        this(7, "d");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new SwapLeftPanelEvent(SwapLeftPanelEvent.PanelType.STATISTIC));
        EventsCenter.getInstance().post(new ShowStatsRequestEvent());
        model.updateExpenseStats(getStatsPredicate());
        model.updateStatsMode(this.mode);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns Predicate used for FilteredList based on the current statsMode
     * @return Predicate
     */
    private Predicate<Expense> getStatsPredicate() {
        Calendar now = Calendar.getInstance();
        if (this.mode == StatsMode.DAY) {
            now.add(Calendar.DAY_OF_MONTH, this.numberOfDaysOrMonths * -1);
        } else {
            now.add(Calendar.MONTH, this.numberOfDaysOrMonths * -1);
        }
        return e -> e.getDate().fullDate.after(now);
    }

    private boolean isValidMode(String mode) {
        return "d".equals(mode) || "m".equals(mode);
    }

    private boolean isValidNumber(int num) {
        return num > 0;
    }

    /**
     * Checks equality of current object to another object
     * @param other Object to compare
     * @return true if equal
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatsCommand // instanceof handles nulls
                && numberOfDaysOrMonths == (((StatsCommand) other).numberOfDaysOrMonths)
                && mode.equals(((StatsCommand) other).mode));
    }
}
