package json.generator;

import commands.InputCommands;

public class NextPrevResult extends Result {
    private String user;
    private String message;
    private static final int IS_NEXT = 0;
    private static final int IS_PREV = 1;


    /**
     *
     * @param inputCommand
     * @param audioFileName
     * @param state
     */
    public NextPrevResult(final InputCommands inputCommand, final String audioFileName,
                          final int state) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        if (audioFileName == null && state == IS_NEXT) {
            this.message = "Please load a source before skipping to the next track.";
        } else if (audioFileName == null && state == IS_PREV) {
            this.message = "Please load a source before returning to the previous track.";
        } else if (state == 0) {
            this.message = "Skipped to next track successfully. The current track is "
                    + audioFileName + ".";
        } else {
            this.message = "Returned to previous track successfully. The current track is "
                    + audioFileName + ".";
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
