package json.generator;

import commands.InputCommands;

import java.util.ArrayList;

public class SeeMerchResult extends Result {
    private String user;
    private ArrayList<String> result;

    public SeeMerchResult(final InputCommands inputCommand, final ArrayList<String> result) {
        this.user = inputCommand.getUsername();
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.result = result;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(final String user) {
        this.user = user;
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
