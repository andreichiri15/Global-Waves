package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddPodcastResult extends Result {
    private String user;
    private String message;

    public AddPodcastResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_HOST) {
            this.message = inputCommand.getUsername() + " is not a host.";
        } else if (returnValue == Errors.DUPLICATE_PODCAST) {
            this.message = inputCommand.getUsername() + " has another podcast with the same name.";
        } else if (returnValue == Errors.DUPLICATE_EPISODE) {
            this.message = inputCommand.getUsername() + " has the same episode in this podcast.";
        } else {
            this.message = inputCommand.getUsername() + " has added new podcast successfully.";
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
