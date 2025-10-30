package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

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
            + ": Adds an event to a recruiter identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START + "START (yyyy-MM-dd HH:mm) "
            + PREFIX_END + "END (yyyy-MM-dd HH:mm) "
            + "[" + PREFIX_MODE + "MODE] "
            + "[" + PREFIX_LOCATION + "LOCATION]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_TITLE + "Google Interview "
            + PREFIX_START + "2025-10-12 14:00 "
            + PREFIX_END + "2025-10-12 15:00 "
            + PREFIX_MODE + "F2F "
            + PREFIX_LOCATION + "Google Headquarters "
            + PREFIX_PRIORITY + "H";

    public static final String MESSAGE_SUCCESS = "New event added.\n%1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists.";
    public static final String MESSAGE_CLASH = "This event clashes with an event under %1$s:\n%2$s";

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(event.getStart(), formatter);
        LocalDateTime end = LocalDateTime.parse(event.getEnd(), formatter);

        for (Person person: lastShownList) {
            Set<Event> events = person.getEvents();
            for (Event event: events) {
                LocalDateTime eventStart = LocalDateTime.parse(event.getStart(), formatter);
                LocalDateTime eventEnd = LocalDateTime.parse(event.getEnd(), formatter);

                boolean beforeEvent = end.isBefore(eventStart) || end.isEqual(eventStart);
                boolean afterEvent = start.isAfter(eventEnd) || start.isEqual(eventEnd);

                if (!beforeEvent && !afterEvent) {
                    throw new CommandException(String.format(MESSAGE_CLASH, person.getName(), event));
                }
            }
        }

        personToAddEvent.addEvent(event);
        model.setPerson(lastShownList.get(targetIndex.getZeroBased()), personToAddEvent);
        return new CommandResult(String.format(MESSAGE_SUCCESS, event));
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
