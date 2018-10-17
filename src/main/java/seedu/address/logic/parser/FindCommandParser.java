package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_COST_KEYWORDS_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_DATE_KEYWORDS_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
//@@Author Jiang Chen
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] splitTrimmedArgs = trimmedArgs.split("/");
        if (splitTrimmedArgs.length == 1 || splitTrimmedArgs[0].equals("")) {
            //Ensure args contains at least one prefix or keyword
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_COST, PREFIX_TAG, PREFIX_DATE);

        ensureKeywordsAreValid(keywordsMap);

        return new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
    }

    /**
     *  Check whether all the keywords are valid.
     * @throws ParseException if any keyword entered by user does not conform the expected format.
     * */
    public void ensureKeywordsAreValid(ArgumentMultimap keywordsMap) throws ParseException {
        String nameKeywords = keywordsMap.getValue(PREFIX_NAME).orElse(null);
        String categoryKeywords = keywordsMap.getValue(PREFIX_CATEGORY).orElse(null);
        List<String> tagKeywords = keywordsMap.getAllValues(PREFIX_TAG);
        String dateKeywords = keywordsMap.getValue(PREFIX_DATE).orElse(null);
        String costKeywords = keywordsMap.getValue(PREFIX_COST).orElse(null);

        if (nameKeywords != null && !Name.isValidName(nameKeywords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_NAME_CONSTRAINTS));
        }

        if (categoryKeywords != null && !Category.isValidCategory(categoryKeywords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Category.MESSAGE_CATEGORY_CONSTRAINTS));
        }

        if (!tagKeywords.isEmpty()) {
            for (String tag : tagKeywords) {
                if (!Tag.isValidTagName(tag)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Tag.MESSAGE_TAG_CONSTRAINTS));
                }
            }
        }

        if (dateKeywords != null) {
            String[] dates = dateKeywords.split(":");
            if (dates.length == 1 && !Date.isValidDate(dates[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Date.DATE_FORMAT_CONSTRAINTS));
            }

            if (dates.length == 2 && (!Date.isValidDate(dates[0]) || !Date.isValidDate(dates[1]))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Date.DATE_FORMAT_CONSTRAINTS));
            }

            if (dates.length == 2 && new Date(dates[1]).isEalierThan(new Date(dates[0]))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_RANGE));
            }

            if (dates.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_DATE_KEYWORDS_FORMAT));
            }

        }

        if (costKeywords != null) {
            String[] costs = costKeywords.split(":");
            if (costs.length == 1 && !Cost.isValidCost(costs[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Cost.MESSAGE_COST_CONSTRAINTS));
            }

            if (costs.length == 2 && (!Cost.isValidCost(costs[0]) || !Cost.isValidCost(costs[1]))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Cost.MESSAGE_COST_CONSTRAINTS));
            }

            if (costs.length == 2 && Double.parseDouble(costs[1]) < Double.parseDouble(costs[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_RANGE));
            }

            if (costs.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_COST_KEYWORDS_FORMAT));

            }
        }
    }

}
