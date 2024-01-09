package json.generator;

import commands.InputCommands;
import utils.Errors;

public class RemoveAnnouncementResult extends Result {
    private String user;
    private String message;

    public RemoveAnnouncementResult(final InputCommands inputCommands, final int returnValue) {
        this.command = inputCommands.getCommand();
        this.user = inputCommands.getUsername();
        this.timestamp = inputCommands.getTimestamp();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommands.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_HOST) {
            this.message = inputCommands.getUsername() + " is not a host.";
        } else if (returnValue == Errors.EVENT_NOT_EXIST) {
            this.message = inputCommands.getUsername()
                    + " has no announcement with the given name.";
        } else {
            this.message = inputCommands.getUsername()
                    + " has successfully deleted the announcement.";
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
