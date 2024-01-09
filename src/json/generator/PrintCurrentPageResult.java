package json.generator;

import commands.InputCommands;

public class PrintCurrentPageResult extends Result {
    private String user;
    private String message;

    public PrintCurrentPageResult(final InputCommands inputCommand, final String message) {
        this.user = inputCommand.getUsername();
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        if (message == null) {
            this.message = inputCommand.getUsername() + " is offline.";
        } else {
            this.message = message;
        }
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
