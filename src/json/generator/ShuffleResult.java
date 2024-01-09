package json.generator;

import commands.InputCommands;
import utils.Errors;

public class ShuffleResult extends Result {
    private String user;
    private String message;
    private static final int SHUFFLE_OK = 0;


    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public ShuffleResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == Errors.NOT_LOADED) {
            this.message = "Please load a source before using the shuffle function.";
        } else if (returnValue == Errors.NOT_PLAYLIST) {
            this.message = "The loaded source is not a playlist or an album.";
        } else if (returnValue == Errors.OK) {
            this.message = "Shuffle function activated successfully.";
        } else {
            this.message = "Shuffle function deactivated successfully.";
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
