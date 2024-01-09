package json.generator;

import commands.InputCommands;
import utils.Errors;

public class RemoveAlbumResult extends Result {
    private String user;
    private String message;

    public RemoveAlbumResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + inputCommand.getUsername() + " doesn't exist.";
        } else if (returnValue == Errors.NOT_ARTIST) {
            this.message = inputCommand.getUsername() + " is not an artist.";
        } else if (returnValue == Errors.ALBUM_NOT_EXIST) {
            this.message = inputCommand.getUsername()
                    + " doesn't have an album with the given name.";
        } else if (returnValue == Errors.ALBUM_CANT_DELETE) {
            this.message = inputCommand.getUsername() + " can't delete this album.";
        } else {
            this.message = inputCommand.getUsername() + " deleted the album successfully.";
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
