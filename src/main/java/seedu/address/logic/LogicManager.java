package seedu.address.logic;

import java.text.SimpleDateFormat;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.StatsCommand.StatsMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.exceptions.UserAlreadyExistsException;
import seedu.address.model.expense.Expense;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, NoUserSelectedException,
            UserAlreadyExistsException, NonExistentUserException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() throws NoUserSelectedException {
        return model.getFilteredExpenseList();
    }

    //@@author winsonhys
    @Override
    public Budget getMaximumBudget() {
        return model.getMaximumBudget();
    }

    //@@author jonathantjm

    /**
     * Returns a LinkedHashMap containing data for the bar chart, where the key is the x-axis and the value is the
     * y-axis.
     * @return LinkedHashMap of String key and Double value
     * @throws NoUserSelectedException
     */
    public LinkedHashMap<String, Double> getExpenseStats() throws NoUserSelectedException {
        ObservableList<Expense> expenseList = model.getExpenseStats();
        StatsMode statsMode = model.getStatsMode();
        LinkedHashMap<String, Double> stats = new LinkedHashMap<>();
        for (Expense e : expenseList) {
            String period;
            if (statsMode == statsMode.DAY) {
                period = e.getDate().toString();
            } else {
                period = new SimpleDateFormat("MMM-YYYY").format(e.getDate().fullDate.getTime());
            }

            if (stats.containsKey(period)) {
                stats.put(
                        period,
                        stats.get(period) + e.getCost().getCostValue()
                );
            } else {
                stats.put(period, e.getCost().getCostValue());
            }
        }
        return stats;
    }


    //@@author
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    public StatsMode getStatsMode() {
        return model.getStatsMode();
    }
}
