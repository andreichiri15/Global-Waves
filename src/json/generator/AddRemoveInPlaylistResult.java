package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddRemoveInPlaylistResult extends Result {
    private String user;
    private String message;
    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public AddRemoveInPlaylistResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == Errors.PLAYLIST_NOT_EXIST) {
            this.message = "The specified playlist does not exist.";
        } else if (returnValue == Errors.NOT_LOADED) {
            this.message = "Please load a source before adding to or removing from the playlist.";
        } else if (returnValue == Errors.NOT_SONG) {
            this.message = "The loaded source is not a song.";
        } else if (returnValue == Errors.OK) {
            this.message = "Successfully removed from playlist.";
        } else {
            this.message = "Successfully added to playlist.";
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
