package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds one or more tags to an existing person in the address book.
 */
public class AddTagsCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds one or more tags to the person identified by the index number or full name.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME TAG [TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 friends colleague\n"
            + "Example: " + COMMAND_WORD + " Alex Yeoh teammate";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) to %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_TAG = "Some tags already exist for this contact.";
    public static final String MESSAGE_PERSON_NOT_FOUND = Messages.MESSAGE_PERSON_NOT_FOUND;

    private final Index targetIndex;
    private final Name targetName;
    private final Set<Tag> tagsToAdd;

    /**
     * Creates an AddTagCommand using an index.
     */
    public AddTagsCommand(Index targetIndex, Set<Tag> tagsToAdd) {
        requireNonNull(targetIndex);
        requireNonNull(tagsToAdd);
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.tagsToAdd = tagsToAdd;
    }

    /**
     * Creates an AddTagCommand using a name.
     */
    public AddTagsCommand(Name targetName, Set<Tag> tagsToAdd) {
        requireNonNull(targetName);
        requireNonNull(tagsToAdd);
        this.targetName = targetName;
        this.targetIndex = null;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit;

        // Find target person
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToEdit = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personToEdit = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(MESSAGE_PERSON_NOT_FOUND));
        }

        // Merge tags
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        boolean hadDuplicates = !updatedTags.addAll(tagsToAdd);

        // Create edited person
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getNote(),
                updatedTags,
                personToEdit.getPinTimestamp(),
                personToEdit.getEvents(),
                personToEdit.getDateAdded()
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String message = String.format(MESSAGE_ADD_TAG_SUCCESS,
                editedPerson.getName(), tagsToAdd);
        if (hadDuplicates) {
            message += "\n(Note: some tags already existed)";
        }

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddTagsCommand
                && Objects.equals(targetIndex, ((AddTagsCommand) other).targetIndex)
                && Objects.equals(targetName, ((AddTagsCommand) other).targetName)
                && tagsToAdd.equals(((AddTagsCommand) other).tagsToAdd));
    }

}
