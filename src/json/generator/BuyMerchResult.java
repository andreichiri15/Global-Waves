package json.generator;

import commands.InputCommands;
import utils.Errors;

public class BuyMerchResult extends Result {
    private String user;
    private String message;

    public BuyMerchResult(final InputCommands inputCommand, final int returnValue) {
        this.user = inputCommand.getUsername();
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.PAGE_NOT_CORRESPONDENT) {
            this.message = "Cannot buy merch from this page.";
        } else if (returnValue == Errors.MERCH_NOT_EXIST) {
            this.message = "The merch " + inputCommand.getName() + " doesn't exist.";
        } else {
            this.message = user + " has added new merch successfully.";
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
