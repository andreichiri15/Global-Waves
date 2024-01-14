package page.command;

import page.Page;

public interface Command {
    void execute();
    void undo();
    void redo();
}
