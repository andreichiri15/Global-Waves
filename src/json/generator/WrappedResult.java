package json.generator;

import commands.InputCommands;
import library.user.helper.wrapped.WrappedStats;

public class WrappedResult extends Result {
    private String user;
    private WrappedStats result;

    /**
     *
     * @param inputCommand
     * @param result
     */
    public WrappedResult(final InputCommands inputCommand,
                         final WrappedStats result) {
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.command = inputCommand.getCommand();
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
    public WrappedStats getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final WrappedStats result) {
        this.result = result;
    }
}
