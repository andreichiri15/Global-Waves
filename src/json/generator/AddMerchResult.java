package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddMerchResult extends Result {
    private String user;
    private String message;
    private static final int MERCH_DUPLICATE = -2;
    private static final int PRICE_NOT_VALID = -1;


    public AddMerchResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.NOT_ARTIST) {
            this.message = user + " is not an artist.";
        } else if (returnValue == Errors.MERCH_DUPLICATE) {
            this.message = user + " has merchandise with the same name.";
        } else if (returnValue == Errors.PRICE_NOT_VALID) {
            this.message = "Price for merchandise can not be negative.";
        } else {
            this.message = user + " has added new merchandise successfully.";
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
