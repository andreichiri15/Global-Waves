package json.generator;

import commands.InputCommands;

import java.util.ArrayList;

public class GetTop5Result extends Result {
    private ArrayList<String> result;

    /**
     *
     * @param inputCommand
     * @param result
     */
    public GetTop5Result(final InputCommands inputCommand, final ArrayList<String> result) {
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
     * @param newResult
     */
    public void setMessage(final ArrayList<String> newResult) {
        this.result = newResult;
    }
}
