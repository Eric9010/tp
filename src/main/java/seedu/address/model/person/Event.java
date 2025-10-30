package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents an event associated to a person in the address book.
 */
public class Event {
    /**
     * Represents the mode of the event
     */
    public enum Mode {
        F2F, CALL, ZOOM;
    }

    /**
     * Represents the priority of the event
     */
    public enum Priority {
        H, M, L; // High, Medium, Low
    }

    public static final String MESSAGE_CONSTRAINTS = """
            1. Events should have a non-empty title, a non-empty start and a non-empty end, both in the yyyy-MM-dd \
            HH:mm format.
            2. Mode is optional. Valid modes are F2F, CALL and ZOOM.
            3. Location is optional and can take any string values.
            4. Priority is optional. Valid priorities are H, M, L (case-insensitive).
            5. Note: Optional fields do not accept empty strings.
            """;

    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Mode mode;
    private final String location;
    private final Priority priority;

    /**
     * Constructs an event.
     *
     * @param title User input title.
     * @param start User input start date and time.
     * @param end User input end date and time.
     * @param mode User input mode.
     * @param location User input location.
     */
    public Event(String title, String start, String end, String mode, String location, String priority) {
        requireNonNull(title);
        requireNonNull(start);
        requireNonNull(end);

        checkArgument(isValidEvent(title, start, end, mode, location, priority), MESSAGE_CONSTRAINTS);

        this.title = title;
        this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.mode = mode == null ? null : Mode.valueOf(mode.toUpperCase());
        this.location = location;

        if (priority == null || priority.trim().isEmpty()) {
            this.priority = null;
        } else {
            this.priority = Priority.valueOf(priority.toUpperCase());
        }
    }

    public Priority getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getEnd() {
        return end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getMode() {
        return mode == null ? "" : mode.toString();
    }

    public String getLocation() {
        return location;
    }

    /**
     * Checks if the title is valid.
     *
     * @param title User input title.
     * @return false if title is an empty string, true otherwise.
     */
    public static boolean isValidTitle(String title) {
        return !title.isEmpty();
    }


    /**
     * Checks if the priority is valid.
     *
     * @param priority User input priority.
     * @return true if priority is H, M, L (case-insensitive) or null, false otherwise.
     */
    public static boolean isValidPriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            return true;
        }
        try {
            Priority.valueOf(priority.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if the date and time is valid.
     *
     * @param startString User input start date and time.
     * @param endString User input end date and time.
     * @return true if start and end matches the pattern yyyy-MM-dd HH:mm and start is before end, false otherwise.
     */
    public static boolean isValidStartEnd(String startString, String endString) {
        try {
            LocalDateTime start = LocalDateTime.parse(startString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime end = LocalDateTime.parse(endString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return !start.isAfter(end);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the mode is valid.
     *
     * @param mode User input mode.
     * @return true if mode is null, F2F, CALL or ZOOM (upper or lower case both acceptable), false otherwise.
     */
    public static boolean isValidMode(String mode) {
        if (mode == null) {
            return true;
        }

        try {
            Mode.valueOf(mode.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if location is valid.
     *
     * @param location User input location.
     * @return false if location is an empty string, true otherwise.
     */
    public static boolean isValidLocation(String location) {
        return location == null || !location.isEmpty();
    }

    /**
     * Checks if the event is valid.
     *
     * @param title User input title.
     * @param start User input start date and time.
     * @param end User input end date and time.
     * @param mode User input mode.
     * @param location User input location.
     * @return true if the all fields of the event is valid, false otherwise.
     */
    public static boolean isValidEvent(String title, String start, String end, String mode, String location,
                                       String priority) {
        return isValidTitle(title) && isValidStartEnd(start, end) && isValidMode(mode) && isValidLocation(location)
                && isValidPriority(priority);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return title.equals(otherEvent.title)
                && start.equals(otherEvent.start)
                && end.equals(otherEvent.end)
                && Objects.equals(mode, otherEvent.mode)
                && Objects.equals(location, otherEvent.location)
                && Objects.equals(priority, otherEvent.priority);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String modeString = mode == null ? "" : " " + mode;
        String locationString = location == null ? "" : " " + location;

        return title + " " + start.format(dateTimeFormatter) + " to " + end.format(dateTimeFormatter) + modeString
                + locationString;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end, mode, location, priority);
    }
}
