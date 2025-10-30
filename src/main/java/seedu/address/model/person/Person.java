package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Note note;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Event> events = new HashSet<>();
    private final Long pinTimestamp;
    private final LocalDateTime dateAdded;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Note note, Set<Tag> tags, Set<Event> events) {
        requireAllNonNull(name, phone, email, address, note, tags, events);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.tags.addAll(tags);
        this.pinTimestamp = null;
        this.events.addAll(events);
        this.dateAdded = LocalDateTime.now();
    }

    /**
     * Second constructor for pinned contacts.
     */
    public Person(Name name, Phone phone, Email email, Address address, Note note, Set<Tag> tags, Long pinTimestamp,
                  Set<Event> events) {
        requireAllNonNull(name, phone, email, address, tags, events);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.tags.addAll(tags);
        this.pinTimestamp = pinTimestamp;
        this.events.addAll(events);
        this.dateAdded = LocalDateTime.now();
    }

    /**
     * Third constructor with all fields.
     */
    public Person(Name name, Phone phone, Email email, Address address, Note note, Set<Tag> tags, Long pinTimestamp,
                  Set<Event> events, LocalDateTime dateAdded) {
        requireAllNonNull(name, phone, email, address, tags, events);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.note = note;
        this.tags.addAll(tags);
        this.pinTimestamp = pinTimestamp;
        this.events.addAll(events);
        this.dateAdded = dateAdded;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Note getNote() {
        return note;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Check if the Person object already contains the event.
     * @param event Event to be compared.
     * @return true if there is an identical event, false otherwise.
     */
    public boolean hasEvent(Event event) {
        return events.contains(event);
    }

    /**
     * Returns an immutable events set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Event> getEvents() {
        return Collections.unmodifiableSet(events);
    }

    public Long getPinTimestamp() {
        return pinTimestamp;
    }

    public boolean isPinned() {
        return pinTimestamp != null;
    }

    /**
     * Returns a new Person object with the pin timestamp set to the current time.
     */
    public Person pin() {
        return new Person(name, phone, email, address, note, tags, System.currentTimeMillis(), this.events,
                dateAdded);
    }

    /**
     * Returns a new Person object with the pin timestamp removed.
     */
    public Person unpin() {
        return new Person(name, phone, email, address, note, tags, null, this.events, dateAdded);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && note.equals(otherPerson.note)
                && tags.equals(otherPerson.tags)
                && Objects.equals(pinTimestamp, otherPerson.pinTimestamp)
                && events.equals(otherPerson.events);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, pinTimestamp, events);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("organisation", address)
                .add("note", note)
                .add("tags", tags)
                .add("events", events)
                .toString();
    }
}
