package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_NONE_FOUND;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_TODAY;
import static seedu.address.logic.commands.RemindCommand.MESSAGE_TOMORROW;
import static seedu.address.logic.commands.RemindCommand.formatNumberedList;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RemindCommandTest {
    private static final RemindCommand REMIND_COMMAND = new RemindCommand(LocalDateTime.of(2025, 10,
            22, 16, 0));
    private static final Event BEFORE_CURRENT_TIME = new Event("Google Interview", "2025-10-22 15:00",
            "2025-10-22 17:00", "f2f", "Google Headquarters", "Final Round");
    private static final Event TODAY = new Event("Google Interview", "2025-10-22 17:00",
            "2025-10-22 18:00", "f2f", "Google Headquarters", "Final Round");
    private static final Event TOMORROW = new Event("Google Interview", "2025-10-23 17:00",
            "2025-10-23 18:00", "f2f", "Google Headquarters", "Final Round");
    private static final Event AFTER_TOMORROW = new Event("Google Interview", "2025-10-24 17:00",
            "2025-10-24 18:00", "f2f", "Google Headquarters", "Final Round");

    @Test
    public void execute_todayAndTomorrow_success() {
        Person person = new PersonBuilder(ALICE).build();
        List<Event> events = List.of(BEFORE_CURRENT_TIME, TODAY, TOMORROW, AFTER_TOMORROW);
        ModelStubWithPersonAndEvents model = new ModelStubWithPersonAndEvents(person, events);
        String expectedMessage = String.format(MESSAGE_SUCCESS, formatNumberedList(List.of(TODAY)),
                formatNumberedList(List.of(TOMORROW)));
        assertCommandSuccess(REMIND_COMMAND, model, expectedMessage, model);
    }

    @Test
    public void execute_onlyToday_success() {
        Person person = new PersonBuilder(ALICE).build();
        List<Event> events = List.of(BEFORE_CURRENT_TIME, TODAY, AFTER_TOMORROW);
        ModelStubWithPersonAndEvents model = new ModelStubWithPersonAndEvents(person, events);
        String expectedMessage = String.format(MESSAGE_TODAY, formatNumberedList(List.of(TODAY)));
        assertCommandSuccess(REMIND_COMMAND, model, expectedMessage, model);
    }

    @Test
    public void execute_onlyTomorrow_success() {
        Person person = new PersonBuilder(ALICE).build();
        List<Event> events = List.of(BEFORE_CURRENT_TIME, TOMORROW, AFTER_TOMORROW);
        ModelStubWithPersonAndEvents model = new ModelStubWithPersonAndEvents(person, events);
        String expectedMessage = String.format(MESSAGE_TOMORROW, formatNumberedList(List.of(TOMORROW)));
        assertCommandSuccess(REMIND_COMMAND, model, expectedMessage, model);
    }

    @Test
    public void execute_noneFound_success() {
        // no events
        Person person = new PersonBuilder(ALICE).build();
        ModelStubWithPerson model = new ModelStubWithPerson(person);
        assertCommandSuccess(REMIND_COMMAND, model, MESSAGE_NONE_FOUND, model);

        // events are before current time
        person = new PersonBuilder(ALICE).build();
        List<Event> events = List.of(BEFORE_CURRENT_TIME);
        ModelStubWithPersonAndEvents otherModel = new ModelStubWithPersonAndEvents(person, events);
        assertCommandSuccess(REMIND_COMMAND, otherModel, MESSAGE_NONE_FOUND, otherModel);

        // events occur after tomorrow
        person = new PersonBuilder(ALICE).build();
        events = List.of(AFTER_TOMORROW);
        otherModel = new ModelStubWithPersonAndEvents(person, events);
        assertCommandSuccess(REMIND_COMMAND, otherModel, MESSAGE_NONE_FOUND, otherModel);
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePersonListSort() { }

        @Override
        public void pinPerson(Person person) { }

        @Override
        public void unpinPerson(Person person) { }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(List.of(person));
        }
    }

    /**
     * A Model stub that contains a single person with one or more events.
     */
    private class ModelStubWithPersonAndEvents extends ModelStub {
        private final Person person;

        ModelStubWithPersonAndEvents(Person person, List<Event> events) {
            requireNonNull(person);
            requireAllNonNull(events);
            this.person = person;
            for (Event event: events) {
                this.person.addEvent(event);
            }
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(List.of(person));
        }
    }
}
