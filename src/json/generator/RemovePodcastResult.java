package json.generator;

import commands.InputCommands;
import utils.Errors;

public class RemovePodcastResult extends Result {
    private String user;
    private String message;

    public RemovePodcastResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.NOT_HOST) {
            this.message = user + " is not a host.";
        } else if (returnValue == Errors.PODCAST_NOT_EXIST) {
            this.message = user + " doesn't have a podcast with the given name.";
        } else if (returnValue == Errors.PODCAST_LOADED) {
            this.message = user + " can't delete this podcast.";
        } else {
            this.message = user + " deleted the podcast successfully.";
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
