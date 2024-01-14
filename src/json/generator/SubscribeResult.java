package json.generator;

import commands.InputCommands;
import library.User;
import utils.Errors;

public class SubscribeResult extends Result {
    private String user;
    private String message;

    public SubscribeResult(InputCommands inputCommand, int returnValue,
                           User userSubscribedTo) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.PAGE_NOT_CORRESPONDENT) {
            this.message = "To subscribe you need to be on the page of an artist or host.";
        } else if (returnValue == Errors.HAS_SUBSCRIBED) {
            this.message = user + " subscribed to " + userSubscribedTo.getUsername()
                    + " successfully.";
        } else {
            this.message = user + " unsubscribed from " + userSubscribedTo.getUsername()
                    + " successfully.";
        }

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
