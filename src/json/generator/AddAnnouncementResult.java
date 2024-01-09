package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddAnnouncementResult extends Result {
    private String user;
    private String message;
    private static final int EVENT_SAME_NAME = -1;


    public AddAnnouncementResult(final InputCommands inputCommands, final int returnValue) {
        this.command = inputCommands.getCommand();
        this.user = inputCommands.getUsername();
        this.timestamp = inputCommands.getTimestamp();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommands.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_HOST) {
            this.message = inputCommands.getUsername() + " is not a host.";
        } else if (returnValue == Errors.EVENT_DUPLICATE) {
            this.message = inputCommands.getUsername() + " has already added an "
                    + "announcement with this name.";
        } else {
            this.message = inputCommands.getUsername() + " has successfully added new "
                    + "announcement.";
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
