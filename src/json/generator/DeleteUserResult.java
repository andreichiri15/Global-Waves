package json.generator;

import commands.InputCommands;
import utils.Errors;

public class DeleteUserResult extends Result {
    private String user;
    private String message;

    public DeleteUserResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername()
                    + " doesn't exist.";
        } else if (returnValue == Errors.USER_IN_ACTION) {
            this.message = inputCommand.getUsername() + " can't be deleted.";
        } else {
            this.message = inputCommand.getUsername() + " was successfully deleted.";
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
