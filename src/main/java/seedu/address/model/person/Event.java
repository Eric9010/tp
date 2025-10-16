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

    public static final String MESSAGE_CONSTRAINTS = """
            1. Events should have a non-empty title, a non-empty date in the YYYY-MM-DD format and a \
            non-empty time in the HH:mm format.
            2. Mode is optional. Valid modes are F2F, CALL and ZOOM.
            3. Location is optional and can take any string values.
            4. Remarks is optional and can take any string values with a maximum of 500 characters.
            5. Note: Optional fields do not accept empty strings.
            """;

    private final String title;
    private final LocalDate date;
    private final LocalTime time;
    private final Mode mode;
    private final String location;
    private final String remarks;

    /**
     * Constructs an event.
     *
     * @param title User input title.
     * @param date User input date.
     * @param time User input time.
     * @param mode User input mode.
     * @param location User input location.
     * @param remarks User input remarks.
     */
    public Event(String title, String date, String time, String mode, String location, String remarks) {
        requireNonNull(title);
        requireNonNull(date);
        requireNonNull(time);

        checkArgument(isValidEvent(title, date, time, mode, location, remarks), MESSAGE_CONSTRAINTS);

        this.title = title;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        this.mode = mode == null ? null : Mode.valueOf(mode.toUpperCase());
        this.location = location;
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getMode() {
        return mode == null ? "" : mode.toString();
    }

    public String getLocation() {
        return location;
    }

    public String getRemarks() {
        return remarks;
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
     * Checks if the date is valid.
     *
     * @param date User input date.
     * @return true if date matches the pattern yyyy-MM-dd, false otherwise.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the time is valid.
     *
     * @param time User input time.
     * @return true if time matches the pattern HH:mm, false otherwise.
     */
    public static boolean isValidTime(String time) {
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
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
     * Checks if remarks is valid.
     *
     * @param remarks User input remarks.
     * @return false if remarks is an empty string or a string with over 500 characters, true otherwise.
     */
    public static boolean isValidRemark(String remarks) {
        return remarks == null || (!remarks.isEmpty() && remarks.length() <= 500);
    }

    /**
     * Checks if the event is valid.
     *
     * @param title User input title.
     * @param date User input date.
     * @param time User input time.
     * @param mode User input mode.
     * @param location User input location.
     * @param remarks User input remarks.
     * @return true if the all fields of the event is valid, false otherwise.
     */
    public static boolean isValidEvent(String title, String date, String time, String mode, String location,
                                       String remarks) {
        return isValidTitle(title) && isValidDate(date) && isValidTime(time) && isValidMode(mode)
                && isValidLocation(location) && isValidRemark(remarks);
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
        return title.equals(otherEvent.title) && date.equals(otherEvent.date) && time.equals(otherEvent.time);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String modeString = mode == null ? "" : " " + mode;
        String locationString = location == null ? "" : " " + location;
        String remarksString = remarks == null ? "" : "\nRemarks: " + remarks;

        return title + " " + date.format(dateFormatter) + " " + time.format(timeFormatter) + modeString
                + locationString + remarksString;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, time);
    }
}
