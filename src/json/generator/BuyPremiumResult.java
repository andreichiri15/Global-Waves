package json.generator;

import commands.InputCommands;
import utils.Errors;

public class BuyPremiumResult extends Result {
    private String user;
    private String message;

    public BuyPremiumResult(final InputCommands inputCommand, final int returnValue) {
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.command = inputCommand.getCommand();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.USER_ALREADY_SUBSCRIBED){
            this.message = user +  " is already a premium user.";
        } else {
            this.message = user + " bought the subscription successfully.";
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
     * @param username
     */
    public void setUser(final String username) {
        this.user = username;
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
