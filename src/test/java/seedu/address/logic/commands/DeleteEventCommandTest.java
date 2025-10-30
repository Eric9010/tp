package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_SUCCESS;
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

public class DeleteEventCommandTest {
    public static final Event VALID_EVENT = new Event("Google Interview", "2025-09-10 15:00",
            "2025-09-10 15:50", "f2f", "Google Headquarters", null);
    public static final DeleteEventCommand DELETE_EVENT_COMMAND = new DeleteEventCommand(Index.fromOneBased(1),
            Index.fromOneBased(1));

    @Test
    public void constructor_hasEvent_success() {
        assertDoesNotThrow(() -> new DeleteEventCommand(Index.fromZeroBased(1),
                Index.fromZeroBased(2)));
    }

    @Test
    public void execute_validIndices_deleteEvent() {
        Person person = new PersonBuilder(ALICE).build();
        ModelStubWithPersonAndEvent model = new ModelStubWithPersonAndEvent(person);

        String expectedMessage = String.format(MESSAGE_SUCCESS, ALICE.getName(), VALID_EVENT);
        assertCommandSuccess(DELETE_EVENT_COMMAND, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidRecruiterIndex_throwsCommandException() {
        assertThrows(CommandException.class, () -> new DeleteEventCommand(Index.fromZeroBased(2),
                Index.fromZeroBased(1)).execute(new ModelStubWithPersonAndEvent(
                        new PersonBuilder(ALICE).build())));
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        assertThrows(CommandException.class, () -> new DeleteEventCommand(Index.fromZeroBased(1),
                Index.fromZeroBased(2))
                .execute(new ModelStubWithPersonAndEvent(new PersonBuilder(ALICE).build())));
    }

    @Test
    public void equals_sameDeleteEventCommand() {
        assertEquals(DELETE_EVENT_COMMAND, DELETE_EVENT_COMMAND);
    }

    @Test
    public void equals_notDeleteEventCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(Index.fromOneBased(1));
        assertNotEquals(DELETE_EVENT_COMMAND, deleteCommand);
    }

    @Test
    public void equals_sameIndices() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(Index.fromOneBased(1),
                Index.fromOneBased(1));
        assertEquals(DELETE_EVENT_COMMAND, deleteEventCommand);
    }

    @Test
    public void equals_differentRecruiterIndex() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(Index.fromOneBased(2),
                Index.fromOneBased(1));
        assertNotEquals(DELETE_EVENT_COMMAND, deleteEventCommand);
    }

    @Test
    public void equals_differentEventIndex() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(Index.fromOneBased(1),
                Index.fromOneBased(2));
        assertNotEquals(DELETE_EVENT_COMMAND, deleteEventCommand);
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

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
        }
    }
}
