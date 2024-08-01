package command;

import model.LibraryService;

public abstract class Command {
    protected LibraryService service = null;

    public abstract void execute();
}
