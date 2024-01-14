package page.command;

import utils.Errors;

import java.util.LinkedList;

public class CommandManager {
    LinkedList<Command> commands = new LinkedList<>();

    public void changePage(Command command) {
        commands.add(command);
        command.execute();
    }

    public int undo() {
        if (commands.isEmpty()) {
            return Errors.HISTORY_EMPTY_PREV;
        }

        Command command = commands.pop();
        if (command != null) {
            command.undo();
        }

        return Errors.SUCCES_PREV;
    }
}
