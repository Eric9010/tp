package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;

/**
 * Reminds users by showing a list of events occurring today and tomorrow.
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of events occurring today and tomorrow.";

    public static final String MESSAGE_SUCCESS = """
            These events are happening today:
            %1$s
            These events are happening tomorrow:
            %2$s""";
    public static final String MESSAGE_TODAY = """
            These events are happening today:
            %1$s
            There are no events happening tomorrow.""";
    public static final String MESSAGE_TOMORROW = """
            There are no events happening today.
            These events are happening tomorrow:
            %1$s""";
    public static final String MESSAGE_NONE_FOUND = "There are no events happening today and tomorrow.";

    private final LocalDateTime currentDateTime;

    /**
     * Creates a RemindCommand. The current time is initialised here.
     */
    public RemindCommand() {
        currentDateTime = LocalDateTime.now();
    }

    /**
     * Creates a RemindCommand. Used for testing purposes only.
     * @param dateTime Test date and time.
     */
    RemindCommand(LocalDateTime dateTime) {
        currentDateTime = dateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Event> eventsToday = new ArrayList<>();
        List<Event> eventsTomorrow = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Person person: lastShownList) {
            Set<Event> events = person.getEvents();
            eventsToday.addAll(events.stream()
                    .filter(event -> LocalDateTime.parse(event.getStart(), formatter).toLocalDate()
                            .isEqual(currentDateTime.toLocalDate()))
                    .filter(event -> LocalDateTime.parse(event.getStart(), formatter).isAfter(currentDateTime))
                    .toList());
            eventsTomorrow.addAll(events.stream()
                    .filter(event -> LocalDateTime.parse(event.getStart(), formatter).toLocalDate()
                            .isEqual(currentDateTime.toLocalDate().plusDays(1)))
                    .toList());
        }

        if (eventsToday.isEmpty() && eventsTomorrow.isEmpty()) {
            return new CommandResult(MESSAGE_NONE_FOUND);
        }

        if (eventsToday.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_TOMORROW, formatNumberedList(eventsTomorrow)));
        }

        if (eventsTomorrow.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_TODAY, formatNumberedList(eventsToday)));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, formatNumberedList(eventsToday),
                formatNumberedList(eventsTomorrow)));
    }

    /**
     * Formats list of events into a numbered list.
     * @param eventList List of events
     * @return String which shows the eventList as a numbered list.
     */
    public static String formatNumberedList(List<Event> eventList) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < eventList.size(); i++) {
            str.append(i + 1).append(". ").append(eventList.get(i)).append("\n");
        }

        return str.toString();
    }
}
