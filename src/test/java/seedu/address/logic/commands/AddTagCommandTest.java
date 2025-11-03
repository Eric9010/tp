package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests for {@link AddTagsCommand}.
 */
public class AddTagCommandTest {

    @Test
    public void execute_addTagByIndex_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("friend"));

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, tagsToAdd);

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getNote(),
                mergeTags(personToEdit, tagsToAdd),
                personToEdit.getPinTimestamp(),
                personToEdit.getEvents(),
                personToEdit.getDateAdded()
        );

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAG_SUCCESS,
                editedPerson.getName(), tagsToAdd);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTag_addsNote() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Tag existing = person.getTags().iterator().next();
        Set<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(existing);

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, tagsToAdd);

        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAG_SUCCESS,
                person.getName(), tagsToAdd)
                + "\n(Note: some tags already existed)";

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("colleague"));

        AddTagsCommand command = new AddTagsCommand(invalidIndex, tags);

        CommandException ex = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ex.getMessage());
    }

    private Set<Tag> mergeTags(Person p, Set<Tag> tagsToAdd) {
        Set<Tag> merged = new HashSet<>(p.getTags());
        merged.addAll(tagsToAdd);
        return merged;
    }
}
