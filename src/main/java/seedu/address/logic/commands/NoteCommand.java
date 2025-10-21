package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class NoteCommand extends Command {
    public static final String COMMAND_WORD = "note";

    private final Index index;
    private final String note;
    
    public NoteCommand(Index index, String note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("hello from note");
    }
}
