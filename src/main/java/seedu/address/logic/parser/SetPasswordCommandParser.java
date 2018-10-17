package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_PASSWORD;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Optional;

import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Password;

//@@author JasonChong96
/**
 * Parses input arguments and creates a new SetPasswordCommand object
 */
public class SetPasswordCommandParser implements Parser<SetPasswordCommand> {
    @Override
    public SetPasswordCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_OLD_PASSWORD, PREFIX_NEW_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_NEW_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }
        Password newPassword = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_NEW_PASSWORD).get());
        Optional<String> oldPassword = argMultimap.getValue(PREFIX_OLD_PASSWORD);
        if (oldPassword.isPresent()) {
            return new SetPasswordCommand(ParserUtil.parsePassword(oldPassword.get()), newPassword);
        } else {
            return new SetPasswordCommand(null, newPassword);
        }
    }
}
