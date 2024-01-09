package json.generator;

import commands.InputCommands;
import utils.Errors;

public class SwitchConnectivityResult extends Result {
    private String user;
    private String message;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public SwitchConnectivityResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.USER_NOT_NORMAL) {
            this.message = inputCommand.getUsername() + " is not a normal user.";
        } else {
            this.message = inputCommand.getUsername() + " has changed status successfully.";
        }
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
}
