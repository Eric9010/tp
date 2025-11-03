package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Filters recruiters by tags.
 */
public class FilterTagCommand extends Command {
    public static final String COMMAND_WORD = "filtertag";

    public static final String MESSAGE_USAGE = "Format: " + COMMAND_WORD
            + " TAG [MORE TAGS]...\n"
            + "Example: " + COMMAND_WORD + " Friend Colleague";

    private final List<String> taglist;

    public FilterTagCommand(List<String> taglist) {
        this.taglist = taglist;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (taglist.stream().anyMatch(t -> !t.matches("[A-Za-z0-9]+"))) {
            throw new CommandException("Invalid tag detected. Tags must be alphanumeric.");
        }

        model.updateFilteredPersonList(person -> hasMatchingTag(person, taglist));

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

    private boolean hasMatchingTag(Person person, List<String> tagKeywords) {
        for (String keyword : tagKeywords) {
            for (Tag tag : person.getTags()) {
                if (tag.tagName.equalsIgnoreCase(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterTagCommand)) {
            return false;
        }

        FilterTagCommand otherCommand = (FilterTagCommand) other;
        return taglist.equals(otherCommand.taglist);
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{tagKeywords=" + taglist + "}";
    }

}
