package page.command;

import utils.Errors;

import java.util.LinkedList;

public class CommandManager {
    LinkedList<Command> commands = new LinkedList<>();
    LinkedList<Command> undoCommands = new LinkedList<>();

    public void changePage(Command command) {
        commands.push(command);
        command.execute();
        undoCommands.clear();
    }

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
}
