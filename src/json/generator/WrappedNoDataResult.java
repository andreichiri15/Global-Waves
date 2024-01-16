package json.generator;

import commands.InputCommands;

public class WrappedNoDataResult extends Result {
    private String user;
    private String message;

    public WrappedNoDataResult(InputCommands inputCommand) {
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.command = inputCommand.getCommand();
        this.message = "No data to show for user " + user + ".";
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
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
