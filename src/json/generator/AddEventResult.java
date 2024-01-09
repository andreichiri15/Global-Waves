package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddEventResult extends Result {
    private String user;
    private String message;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public AddEventResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_ARTIST) {
            this.message = inputCommand.getUsername() + " is not an artist.";
        } else if (returnValue == Errors.EVENT_DUPLICATE) {
            this.message = inputCommand.getUsername() + " has another event with the same name.";
        } else if (returnValue == Errors.DATE_INVALID) {
            this.message = "Event for " + inputCommand.getUsername()
                    + " does not have a valid date.";
        } else {
            this.message = inputCommand.getUsername() + " has added new event successfully.";
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
