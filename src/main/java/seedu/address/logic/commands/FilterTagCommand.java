package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Filters persons by tags.
 */
public class FilterTagCommand extends Command {
    public static final String COMMAND_WORD = "filtertag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose tags match any of "
            + "the specified tags and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " Friend Colleague";

    private final List<String> taglist;

    public FilterTagCommand(List<String> taglist) {
        this.taglist = taglist;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(person -> {
            Set<Tag> tags = person.getTags();
            for (String keyword : taglist) {
                for (Tag tag : tags) {
                    if (tag.tagName.equalsIgnoreCase(keyword)) {
                        return true;
                    }
                }
            }
            return false;
        });

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }

    /**
     * Parses arguments and creates FilterTagCommand.
     */
    public static FilterTagCommand parse(String args) throws Exception {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new Exception("Tag keywords cannot be empty!");
        }
        return new FilterTagCommand(Arrays.asList(trimmed.split("\\s+")));
    }

}
