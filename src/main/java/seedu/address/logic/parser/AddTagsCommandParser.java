package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object.
 */
public class AddTagsCommandParser {
    /**
     * Parses the given {@code String} of arguments and returns an AddTagCommand object.
     * @throws ParseException if the user input is invalid.
     */
    public AddTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+");
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagsCommand.MESSAGE_USAGE));
        }

        // Try to parse the first argument as an index
        try {
            Index index = ParserUtil.parseIndex(parts[0]);
            Set<Tag> tags = parseTags(parts, 1);
            return new AddTagsCommand(index, tags);
        } catch (ParseException e) {
            // Fallback: treat it as a name
            Name name = new Name(parts[0]);
            Set<Tag> tags = parseTags(parts, 1);
            return new AddTagsCommand(name, tags);
        }
    }

    /**
     * Converts the remaining arguments into Tag objects.
     */
    private Set<Tag> parseTags(String[] parts, int startIndex) throws ParseException {
        Set<Tag> tags = new HashSet<>();
        for (int i = startIndex; i < parts.length; i++) {
            tags.add(ParserUtil.parseTag(parts[i]));
        }
        return tags;
    }
}
