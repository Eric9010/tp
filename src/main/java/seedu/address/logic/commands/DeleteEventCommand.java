package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;

/**
 * Deletes an event identified by the recruiter index number and event index number.
 */
public class DeleteEventCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an event identified by the recruiter index number and event index number.\n"
            + "Parameters: RECRUITER_INDEX (positive integer) EVENT_INDEX (positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2 3";

    public static final String MESSAGE_SUCCESS = "The event under %1$s is deleted.\n%2$s";

    private final Index recruiterIndex;
    private final Index eventIndex;

    /**
     * Creates a DeleteEventCommand.
     * @param recruiterIndex Index of recruiter which has the event to be deleted.
     * @param eventIndex Index of event to be deleted.
     */
    public DeleteEventCommand(Index recruiterIndex, Index eventIndex) {
        this.recruiterIndex = recruiterIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (recruiterIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteEvent = lastShownList.get(recruiterIndex.getZeroBased());

        if (eventIndex.getZeroBased() >= personToDeleteEvent.getEvents().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ArrayList<Event> eventList = new ArrayList<>(personToDeleteEvent.getEvents());
        Event eventToDelete = eventList.get(eventIndex.getZeroBased());
        personToDeleteEvent.deleteEvent(eventToDelete);
        model.setPerson(lastShownList.get(recruiterIndex.getZeroBased()), personToDeleteEvent);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToDeleteEvent.getName(), eventToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteEventCommand)) {
            return false;
        }

        DeleteEventCommand otherEventCommand = (DeleteEventCommand) other;
        return recruiterIndex.equals(otherEventCommand.recruiterIndex)
                && eventIndex.equals(otherEventCommand.eventIndex);
    }
}
