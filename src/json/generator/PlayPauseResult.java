package json.generator;

import commands.InputCommands;

public class PlayPauseResult extends Result {
    private String user;
    private String message;
    private static final int NOT_LOADED = -1;
    private static final int IS_RESUME = 0;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public PlayPauseResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        if (returnValue == NOT_LOADED) {
            this.message = "Please load a source before attempting to pause or resume playback.";
        } else if (returnValue == IS_RESUME) {
            this.message = "Playback resumed successfully.";
        } else {
            this.message = "Playback paused successfully.";
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
