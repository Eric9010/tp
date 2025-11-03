package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;

import java.time.LocalDate;
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
 * Finds a duration of time when the user does not have any events.
 */
public class FreeTimeCommand extends Command {
    private class Duration {
        private LocalDateTime start;
        private LocalDateTime end;

        private Duration(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return "[" + start.format(formatter) + " to " + end.format(formatter) + "]";
        }
    }

    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = "Format: " + COMMAND_WORD
            + " " + PREFIX_HOURS + "NO_OF_HOURS (1 - 16) "
            + PREFIX_DATE + "DATE (YYYY-MM-DD)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HOURS + "2 "
            + PREFIX_DATE + "2025-10-13\n";

    public static final String MESSAGE_SUCCESS = "%1$s %2$s can fit a %3$s-hour event:\n%4$s\n";
    public static final String MESSAGE_NOT_FOUND = "No such time slots can be found.\n";

    private final int hours;
    private final LocalDate date;

    /**
     * Creates a FreeTimeCommand
     * @param hours User input number of hours.
     * @param date User input date.
     */
    public FreeTimeCommand(int hours, LocalDate date) {
        requireNonNull(date);
        this.hours = hours;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Duration> availableTimes = new ArrayList<>();
        LocalDateTime start = date.atStartOfDay().plusHours(7);
        LocalDateTime end = start.plusHours(hours);

        while (end.isBefore(date.atStartOfDay().plusHours(23).plusSeconds(1))) {
            availableTimes.add(new Duration(start, end));
            start = start.plusMinutes(15);
            end = end.plusMinutes(15);
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Person person: lastShownList) {
            Set<Event> events = person.getEvents();

            for (Event event: events) {
                LocalDateTime eventStart = LocalDateTime.parse(event.getStart(), formatter);
                LocalDateTime eventEnd = LocalDateTime.parse(event.getEnd(), formatter);
                Duration eventDuration = new Duration(eventStart, eventEnd);

                availableTimes = availableTimes.stream()
                        .filter(time -> notOverlapping(time, eventDuration))
                        .toList();
            }
        }

        if (availableTimes.isEmpty()) {
            return new CommandResult(MESSAGE_NOT_FOUND);
        }

        List<Duration> combinedTimes = new ArrayList<>();
        LocalDateTime intervalStart = availableTimes.get(0).start;
        LocalDateTime intervalEnd = availableTimes.get(0).end;
        for (int i = 0; i < availableTimes.size() - 1; i++) {
            if (intervalEnd.plusMinutes(15).isEqual(availableTimes.get(i + 1).end)) {
                intervalEnd = intervalEnd.plusMinutes(15);
            } else {
                combinedTimes.add(new Duration(intervalStart, intervalEnd));
                intervalStart = availableTimes.get(i + 1).start;
                intervalEnd = availableTimes.get(i + 1).end;
            }
        }

        if (intervalEnd.isBefore(date.atStartOfDay().plusHours(23).plusSeconds(1))) {
            combinedTimes.add(new Duration(intervalStart, intervalEnd));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, combinedTimes.size(),
                combinedTimes.size() == 1 ? "time slot" : "time slots", hours, formatNumberedList(combinedTimes)));
    }

    /**
     * Checks if the two durations have any overlap.
     * @param duration1 First duration.
     * @param duration2 Second duration.
     * @return true if there is no overlap, false otherwise.
     */
    private boolean notOverlapping(Duration duration1, Duration duration2) {
        boolean isBefore = duration1.end.isBefore(duration2.start) || duration1.end.equals(duration2.start);
        boolean isAfter = duration1.start.isAfter(duration2.end) || duration1.start.equals(duration2.end);
        return isBefore || isAfter;
    }

    /**
     * Formats list of durations into a numbered list.
     * @param durationList List of events
     * @return String which shows the eventList as a numbered list.
     */
    private static String formatNumberedList(List<Duration> durationList) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < durationList.size(); i++) {
            str.append(i + 1).append(". ").append(durationList.get(i)).append("\n");
        }

        return str.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FreeTimeCommand)) {
            return false;
        }

        FreeTimeCommand otherFreeTimeCommand = (FreeTimeCommand) other;
        return hours == otherFreeTimeCommand.hours && date.isEqual(otherFreeTimeCommand.date);
    }
}
