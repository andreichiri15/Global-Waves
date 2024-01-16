package json.generator;

import commands.InputCommands;
import utils.Errors;

public class PreviousNextPageResult extends Result {
    private String user;
    private String message;

    public PreviousNextPageResult(final InputCommands inputCommand, final int returnValue) {
        this.user = inputCommand.getUsername();
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == Errors.HISTORY_EMPTY_PREV) {
            this.message = "There are no pages left to go back.";
        } else if (returnValue == Errors.HISTORY_EMPTY_NEXT) {
            this.message = "There are no pages left to go forward.";
        } else if (returnValue == Errors.SUCCES_PREV) {
            this.message = "The user " + user
                    + " has navigated successfully to the previous page.";
        } else {
            this.message = "The user " + user + " has navigated successfully to the next page.";
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
