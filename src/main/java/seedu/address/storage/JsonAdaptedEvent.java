package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Event;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    private final String title;
    private final String start;
    private final String end;
    private final String mode;
    private final String location;
    private final String remarks;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("title") String title, @JsonProperty("start") String start,
                            @JsonProperty("end") String end, @JsonProperty("mode") String mode,
                            @JsonProperty("location") String location, @JsonProperty("remarks") String remarks) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.mode = mode == null ? "" : mode;
        this.location = location == null ? "" : location;
        this.remarks = remarks == null ? "" : remarks;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        title = source.getTitle();
        start = source.getStart();
        end = source.getEnd();
        mode = source.getMode() == null ? "" : source.getMode();
        location = source.getLocation() == null ? "" : source.getLocation();
        remarks = source.getRemarks() == null ? "" : source.getRemarks();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        String modeUsed = mode.isEmpty() ? null : mode;
        String locationUsed = location.isEmpty() ? null : location;
        String remarksUsed = remarks.isEmpty() ? null : remarks;

        if (!Event.isValidEvent(title, start, end, modeUsed, locationUsed, remarksUsed)) {
            throw new IllegalValueException(Event.MESSAGE_CONSTRAINTS);
        }
        return new Event(title, start, end, modeUsed, locationUsed, remarksUsed);
    }

}

