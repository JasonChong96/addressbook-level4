package seedu.address.logic;

import java.util.LinkedHashMap;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.StatsCommand.StatsMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.Expense;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, NoUserSelectedException,
            UserAlreadyExistsException, NonExistentUserException;

    Budget getMaximumBudget();

    /**
     * @return an unmodifiable view of the filtered list of expenses
     * @throws NoUserSelectedException
     */
    ObservableList<Expense> getFilteredExpenseList() throws NoUserSelectedException;

    /**
     * Returns a LinkedHashMap of expenses for the bar chart where the key is a String which represents the x-axis data
     * and the value is a double which represents the total amount
     * @return a LinkedHashMap of expenses for the bar chart
     * @throws NoUserSelectedException
     */
    LinkedHashMap<String, Double> getExpenseStats() throws NoUserSelectedException;

    /**
     * @return a StatsMode representing the current mode of statistics
     * @throws NoUserSelectedException
     */
    StatsMode getStatsMode() throws NoUserSelectedException;

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

}
