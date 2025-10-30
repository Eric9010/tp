package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EventCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EventCommandTest {
    public static final Event VALID_EVENT = new Event("Google Interview", "2025-09-10 15:00",
            "2025-09-10 15:50", "f2f", "Google Headquarters", "H");
    public static final Event ALTERNATE_VALID_EVENT = new Event("Amazon Interview", "2025-09-10 15:40",
            "2025-09-10 16:50", null, null, null);

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventCommand(Index.fromZeroBased(1),
                null));
    }

    @Test
    public void constructor_hasEvent_success() {
        assertDoesNotThrow(() -> new EventCommand(Index.fromZeroBased(1), VALID_EVENT));
    }

    @Test
    public void execute_validIndexAndEvent_addedToPerson() {
        EventCommand eventCommand = new EventCommand(Index.fromOneBased(1), VALID_EVENT);

        Person person = new PersonBuilder(ALICE).build();
        ModelStubWithPerson model = new ModelStubWithPerson(person);

        Person personWithEvent = new PersonBuilder(ALICE).build();
        personWithEvent.addEvent(VALID_EVENT);

        String expectedMessage = String.format(MESSAGE_SUCCESS, VALID_EVENT);
        assertCommandSuccess(eventCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(2), VALID_EVENT)
                .execute(new ModelStubWithPerson(new PersonBuilder(ALICE).build())));
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1), VALID_EVENT)
                .execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));
    }

    @Test
    public void execute_hasClashes_throwsCommandException() {
        // same time
        Event event1 = new Event("Amazon Interview", "2025-09-10 15:00", "2025-09-10 15:50", null,
                null, null);
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1),
                event1).execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));

        // subset of event duration
        Event event2 = new Event("Amazon Interview", "2025-09-10 15:30", "2025-09-10 15:40", null,
                null, null);
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1),
                event2).execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));

        // superset of event duration
        Event event3 = new Event("Amazon Interview", "2025-09-10 14:30", "2025-09-10 16:40", null,
                null, null);
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1),
                event3).execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));

        // overlap with start
        Event event4 = new Event("Amazon Interview", "2025-09-10 14:30", "2025-09-10 15:40", null,
                null, null);
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1),
                event4).execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));

        // overlap with end
        Event event5 = new Event("Amazon Interview", "2025-09-10 15:30", "2025-09-10 16:40", null,
                null, null);
        assertThrows(CommandException.class, () -> new EventCommand(Index.fromOneBased(1),
                event5).execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));
    }

    @Test
    public void equals_sameEventCommand() {
        EventCommand eventCommand = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        assertEquals(eventCommand, eventCommand);
    }

    @Test
    public void equals_notEventCommand() {
        EventCommand eventCommand = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(1));
        assertNotEquals(eventCommand, deleteCommand);
    }

    @Test
    public void equals_sameAttributes() {
        EventCommand eventCommand1 = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        EventCommand eventCommand2 = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        assertEquals(eventCommand1, eventCommand2);
    }

    @Test
    public void equals_differentIndex() {
        EventCommand eventCommand1 = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        EventCommand eventCommand2 = new EventCommand(Index.fromOneBased(2), VALID_EVENT);
        assertNotEquals(eventCommand1, eventCommand2);
    }

    @Test
    public void equals_differentEvent() {
        EventCommand eventCommand1 = new EventCommand(Index.fromOneBased(1), VALID_EVENT);
        EventCommand eventCommand2 = new EventCommand(Index.fromOneBased(1), ALTERNATE_VALID_EVENT);
        assertNotEquals(eventCommand1, eventCommand2);
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
        public ObservableList<Person> getSortedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonList(Comparator<Person> comparator) {
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

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
        }
    }

    /**
     * A Model stub that contains a single person with a single event.
     */
    private class ModelStubWithPersonAndEvent extends ModelStub {
        private final Person person;

        ModelStubWithPersonAndEvent(Person person) {
            requireNonNull(person);
            this.person = person;
            this.person.addEvent(VALID_EVENT);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableList(List.of(person));
        }
    }
}
