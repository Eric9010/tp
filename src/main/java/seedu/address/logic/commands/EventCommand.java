package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;

/**
 * Adds an event to a recruiter identified by the index number used in the displayed person list.
 */
public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an event to a recruiter identified by the index number used in the displayed\n"
            + "person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_EVENT + "EVENT "
            + PREFIX_DATE + "DATE (yyyy-MM-dd) "
            + PREFIX_TIME + "TIME (HH:mm) "
            + "[" + PREFIX_MODE + "MODE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_REMARKS + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_EVENT + "Google Interview "
            + PREFIX_DATE + "2025-10-12 "
            + PREFIX_TIME + "15:00 "
            + PREFIX_MODE + "F2F "
            + PREFIX_LOCATION + "Google Headquarters\n"
            + PREFIX_REMARKS + "Final Round";

    public static final String MESSAGE_SUCCESS = "New event added.\n%1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists.";

    private final Index targetIndex;
    private final Event event;

    /**
     * Creates an EventCommand.
     * @param targetIndex Index of recruiter to be associated with this event.
     * @param event User input event.
     */
    public EventCommand(Index targetIndex, Event event) {
        requireNonNull(event);
        this.targetIndex = targetIndex;
        this.event = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddEvent = lastShownList.get(targetIndex.getZeroBased());

        if (personToAddEvent.hasEvent(event)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        personToAddEvent.addEvent(event);
        return new CommandResult(String.format(MESSAGE_SUCCESS, event.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCommand)) {
            return false;
        }

        EventCommand otherEventCommand = (EventCommand) other;
        return targetIndex.equals(otherEventCommand.targetIndex) && event.equals(otherEventCommand.event);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("event", event)
                .toString();
    }
}
