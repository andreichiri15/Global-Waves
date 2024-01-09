package json.generator;

import commands.InputCommands;

public class CreatePlaylistResult extends Result {
    private String user;
    private String message;
    private static final int CAN_CREATE_PLAYLIST = 0;

    public CreatePlaylistResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == CAN_CREATE_PLAYLIST) {
            this.message = "Playlist created successfully.";
        } else {
            this.message = "A playlist with the same name already exists.";
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
