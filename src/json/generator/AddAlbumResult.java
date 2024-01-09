package json.generator;

import commands.InputCommands;
import utils.Errors;

public class AddAlbumResult extends Result {
    private String user;
    private String message;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public AddAlbumResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_ARTIST) {
            this.message = inputCommand.getUsername() + " is not an artist.";
        } else if (returnValue == Errors.ALBUM_SAME_NAME) {
            this.message = inputCommand.getUsername() + " has another album with the same name.";
        } else if (returnValue == Errors.DUPLICATE_SONG) {
            this.message = inputCommand.getUsername()
                    + " has the same song at least twice in this album.";
        } else {
            this.message = inputCommand.getUsername() + " has added new album successfully.";
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
