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
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

//@@author Jiang Chen
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // one keyword
        ArgumentMultimap keywordsMap = prepareKeywords("n/Have Lunch");
        FindCommand expectedFindCommand =
                new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
        assertParseSuccess(parser, " n/Have Lunch", expectedFindCommand);
        assertParseSuccess(parser, " n/   Have Lunch", expectedFindCommand);

        // multiple keywords
        keywordsMap = prepareKeywords("n/Buy books c/Book");
        expectedFindCommand = new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
        assertParseSuccess(parser, " n/Buy books c/Book", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_parseFail() {
        //invalid name keywords
        assertParseFailure(parser, " n/Have Lunch@KFC ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_NAME_CONSTRAINTS));

        //invalid category keywords
        assertParseFailure(parser, " c/Lunch@KFC  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Category.MESSAGE_CATEGORY_CONSTRAINTS));

        //invalid tag keywords
        assertParseFailure(parser, " t/Lunch@KFC  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));

        //invalid cost keywords
        assertParseFailure(parser, " $/2.320",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Cost.MESSAGE_COST_CONSTRAINTS));
        assertParseFailure(parser, " $/1.00:1.203",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Cost.MESSAGE_COST_CONSTRAINTS));
        assertParseFailure(parser, " $/5.00:1.00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_RANGE));
        assertParseFailure(parser, " $/1.00:2.00:3.00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_COST_KEYWORDS_FORMAT));

        //invalid date keywords
        assertParseFailure(parser, " d/60-02-2019",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.DATE_FORMAT_CONSTRAINTS));
        assertParseFailure(parser, " d/01-02-2019:99-02-2019",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.DATE_FORMAT_CONSTRAINTS));
        assertParseFailure(parser, " d/10-01-2018:01-01-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_RANGE));
        assertParseFailure(parser, " d/01-01-2018:02-02-2018:02-03-2018",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_DATE_KEYWORDS_FORMAT));

        //missing prefix or keywords
        assertParseFailure(parser, " n/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " /School ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /**
     * Returns an {@code ArgumentMultiMap} which tokenize the {@code arg} based on prefixes.
     * */
    public ArgumentMultimap prepareKeywords(String arg) {
        return ArgumentTokenizer.tokenize(" " + arg,
                PREFIX_NAME, PREFIX_CATEGORY, PREFIX_COST, PREFIX_TAG, PREFIX_DATE);
    }

}
