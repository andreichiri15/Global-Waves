package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AdBreakResult extends Result {
    private String user;
    private String message;

    public AdBreakResult(final InputCommands inputCommand, final int returnValue) {
        this.user = inputCommand.getUsername();
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.USER_NOT_PLAYING) {
            this.message = user + " is not playing any music.";
        } else {
            this.message = "Ad inserted successfully.";
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
