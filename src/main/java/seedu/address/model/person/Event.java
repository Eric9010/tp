package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
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

    private static final String MESSAGE_CONSTRAINTS = """
            1. Events should have a non-empty title, a non-empty date in the YYYY-MM-DD format and a \
            non-empty time in the HH:mm format.
            2. Mode is optional. Valid modes are F2F, CALL and ZOOM.
            3. Location is optional and can take any string values.
            4. Description is optional and can take any string values with a maximum of 500 characters.
            """;

    private final String title;
    private final LocalDate date;
    private final LocalTime time;
    private final Mode mode;
    private final String location;
    private final String description;

    /**
     * Constructs an event.
     *
     * @param title User input title.
     * @param date User input date.
     * @param time User input time.
     * @param mode User input mode.
     * @param location User input location.
     * @param description User input description.
     */
    public Event(String title, String date, String time, String mode, String location, String description) {
        requireNonNull(title);
        requireNonNull(date);
        requireNonNull(time);
        checkArgument(isValidEvent(date, time, mode), MESSAGE_CONSTRAINTS);
        this.title = title;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        this.mode = Mode.valueOf(mode);
        this.location = location;
        this.description = description;
    }

    private boolean isValidTitle() {
        return !title.isEmpty();
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalDate.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidMode(String mode) {
        if (mode == null) {
            return true;
        }
        try {
            Mode.valueOf(mode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidDescription() {
        return description == null || description.length() <= 500;
    }

    /**
     * Checks if the event is valid.
     *
     * @param date User input date.
     * @param time User input time.
     * @param mode User input mode.
     * @return true if the all fields of the event is valid, false otherwise.
     */
    public boolean isValidEvent(String date, String time, String mode) {
        return isValidTitle() && isValidDate(date) && isValidTime(time) && isValidMode(mode) && isValidDescription();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return title.equals(otherEvent.title) && date.equals(otherEvent.date) && time.equals(otherEvent.time);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return title + " " + date.format(dateFormatter) + " " + time.format(timeFormatter) + " " + mode
                + " " + location + " " + description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, time);
    }
}
