package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FreeTimeCommand.MESSAGE_NOT_FOUND;
import static seedu.address.logic.commands.FreeTimeCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Event;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FreeTimeCommandTest {
    private static final Event FULL_DAY = new Event("Interview", "2025-09-10 00:00", "2025-09-11 00:00",
            null, null, null);
    private static final Event HALF_DAY = new Event("Interview", "2025-09-10 00:00", "2025-09-10 12:00",
            null, null, null);
    private static final FreeTimeCommand FREE_TIME_COMMAND = new FreeTimeCommand(10, LocalDate.of(2025,
            9, 10));

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FreeTimeCommand(1, null));
    }

    @Test
    public void constructor_validInputs_success() {
        assertDoesNotThrow(() -> new FreeTimeCommand(1, LocalDate.of(2025, 10, 23)));
    }

    @Test
    public void execute_noneFound() {
        Person person = new PersonBuilder(ALICE).build();
        ModelStubWithPersonAndEvent model = new ModelStubWithPersonAndEvent(person, List.of(FULL_DAY));
        assertCommandSuccess(FREE_TIME_COMMAND, model, MESSAGE_NOT_FOUND, model);
    }

    @Test
    public void execute_success() {
        Person person = new PersonBuilder(ALICE).build();
        ModelStubWithPersonAndEvent model = new ModelStubWithPersonAndEvent(person, List.of(HALF_DAY));
        String expectedMessage = String.format(MESSAGE_SUCCESS, 5, """
                1. [2025-09-10 12:00 to 2025-09-10 22:00]
                2. [2025-09-10 12:15 to 2025-09-10 22:15]
                3. [2025-09-10 12:30 to 2025-09-10 22:30]
                4. [2025-09-10 12:45 to 2025-09-10 22:45]
                5. [2025-09-10 13:00 to 2025-09-10 23:00]
                """);
        assertCommandSuccess(FREE_TIME_COMMAND, model, expectedMessage, model);
    }

    @Test
    public void equals_sameFreeTimeCommand() {
        assertEquals(FREE_TIME_COMMAND, FREE_TIME_COMMAND);
    }

    @Test
    public void equals_notFreeTimeCommand() {
        assertNotEquals(FREE_TIME_COMMAND, new DeleteCommand(Index.fromOneBased(1)));
    }

    @Test
    public void equals_sameAttributes() {
        FreeTimeCommand identical = new FreeTimeCommand(10, LocalDate.of(2025, 9, 10));
        assertEquals(FREE_TIME_COMMAND, identical);
    }

    @Test
    public void equals_differentHours() {
        FreeTimeCommand differentHours = new FreeTimeCommand(12, LocalDate.of(2025, 9, 10));
        assertNotEquals(FREE_TIME_COMMAND, differentHours);
    }

    @Test
    public void equals_differentDate() {
        FreeTimeCommand differentDate = new FreeTimeCommand(10, LocalDate.of(2026, 9, 10));
        assertNotEquals(FREE_TIME_COMMAND, differentDate);
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
     * A Model stub that contains a single person with one or more events.
     */
    private class ModelStubWithPersonAndEvent extends ModelStub {
        private final Person person;

        ModelStubWithPersonAndEvent(Person person, List<Event> events) {
            requireNonNull(person);
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
