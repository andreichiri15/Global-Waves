package page.command;

public interface Command {

    /**
     * method that executes the command
     */
    void execute();

    /**
     * method that undoes the command
     */
    void undo();

    /**
     * method that redoes the command
     */
    void redo();
}
