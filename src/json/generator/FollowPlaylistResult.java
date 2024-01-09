package json.generator;

import commands.InputCommands;
import utils.Errors;

public class FollowPlaylistResult extends Result {
    private String user;
    private String message;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public FollowPlaylistResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == Errors.SOURCE_NOT_SELECTED) {
            this.message = "Please select a source before following or unfollowing.";
        } else if (returnValue == Errors.NOT_PLAYLIST) {
            this.message = "The selected source is not a playlist.";
        } else if (returnValue == Errors.OWN_PLAYLIST) {
            this.message = "You cannot follow or unfollow your own playlist.";
        } else if (returnValue == Errors.OK) {
            this.message = "Playlist unfollowed successfully.";
        } else {
            this.message = "Playlist followed successfully.";
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
