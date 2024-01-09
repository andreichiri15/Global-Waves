package json.generator;

import commands.InputCommands;

public class NextPageResult extends Result {
    private String user;
    private String message;
    private static final int PAGE_NOT_EXIST = -1;

    public NextPageResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == PAGE_NOT_EXIST) {
            this.message = inputCommand.getUsername()
                    + " is trying to access a non-existent page.";
        } else {
            this.message = inputCommand.getUsername() + " accessed " + inputCommand.getNextPage()
                    + " successfully.";
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
