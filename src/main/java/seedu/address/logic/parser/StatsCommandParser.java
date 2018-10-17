package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DAYS_OR_MONTHS;

import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;



//@author jonathantjm

/**
 * Parses input arguments and creates a new StatsCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatsCommand parse(String args) throws ParseException {
        if (args.length() == 0) {
            return new StatsCommand();
        }

        try {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_NUMBER_OF_DAYS_OR_MONTHS, PREFIX_MODE);
            int numberOfDaysOrMonths = Integer.parseInt(argMultimap.getValue(PREFIX_NUMBER_OF_DAYS_OR_MONTHS).get());
            String mode = argMultimap.getValue(PREFIX_MODE).get();

            return new StatsCommand(numberOfDaysOrMonths, mode);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }
    }
}
