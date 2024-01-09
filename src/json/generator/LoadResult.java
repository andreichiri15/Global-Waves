package json.generator;

import commands.InputCommands;

public class LoadResult extends Result {
    private String user;
    private String message;

    private static final int NOT_SOURCE = -1;
    private static final int LOAD_OK = 0;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public LoadResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == LOAD_OK) {
            this.message = "Playback loaded successfully.";
        } else if (returnValue == NOT_SOURCE) {
            this.message = "Please select a source before attempting to load.";
        } else {
            this.message = "You can't load an empty audio collection!";
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
