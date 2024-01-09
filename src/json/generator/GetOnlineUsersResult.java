package json.generator;

import commands.InputCommands;

import java.util.ArrayList;

public class GetOnlineUsersResult extends Result {
    private ArrayList<String> result;

    /**
     *
     * @param inputCommand
     * @param result
     */
    public GetOnlineUsersResult(final InputCommands inputCommand, final ArrayList<String> result) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.result = result;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final ArrayList<String> result) {
        this.result = result;
    }
}
