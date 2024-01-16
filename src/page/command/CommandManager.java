package page.command;

import utils.Errors;

import java.util.LinkedList;

public class CommandManager {
    private LinkedList<Command> commands = new LinkedList<>();
    private LinkedList<Command> undoCommands = new LinkedList<>();

    /**
     * changes the page to the one specified by the command
     * @param command the command that changes the page
     */
    public void changePage(final Command command) {
        commands.push(command);
        command.execute();
        undoCommands.clear();
    }

    /**
     * undo the last command, if possible, and adds it to the undo stack
     * @return the error code
     */
    public int undo() {
        if (commands.isEmpty()) {
            return Errors.HISTORY_EMPTY_PREV;
        }

        Command command = commands.pop();
        if (command != null) {
            undoCommands.push(command);
            command.undo();
        }

        return Errors.SUCCES_PREV;
    }


    /**
     * redoes the last command, if possible, and adds it to the redo stack
     * @return the error code
     */
    public int redo() {
        if (undoCommands.isEmpty()) {
            return Errors.HISTORY_EMPTY_NEXT;
        }

        Command command = undoCommands.pop();
        if (command != null) {
            command.redo();
        }

        return Errors.SUCCES_NEXT;
    }

    /**
     *
     * @return
     */
    public LinkedList<Command> getCommands() {
        return commands;
    }

    /**
     *
     * @param commands
     */
    public void setCommands(final LinkedList<Command> commands) {
        this.commands = commands;
    }

    /**
     *
     * @return
     */
    public LinkedList<Command> getUndoCommands() {
        return undoCommands;
    }

    /**
     *
     * @param undoCommands
     */
    public void setUndoCommands(final LinkedList<Command> undoCommands) {
        this.undoCommands = undoCommands;
    }
}
