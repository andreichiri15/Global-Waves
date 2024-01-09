package json.generator;

import commands.InputCommands;
import utils.Errors;

public class LikeResult extends Result {
    private String user;
    private String message;
    private static final int NOT_LOADED = -2;
    private static final int NOT_SONG = -1;
    private static final int UNLIKE_OK = 0;
    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public LikeResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == Errors.USER_NOT_ONLINE) {
            this.message = inputCommand.getUsername() + " is offline.";
        } else if (returnValue == Errors.NOT_LOADED) {
            this.message = "Please load a source before liking or unliking.";
        } else if (returnValue == Errors.NOT_SONG) {
            this.message = "Loaded source is not a song.";
        } else if (returnValue == Errors.OK) {
            this.message = "Unlike registered successfully.";
        } else {
            this.message = "Like registered successfully.";
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
