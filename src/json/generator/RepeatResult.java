package json.generator;

import commands.InputCommands;

public class RepeatResult extends Result {
    private String user;
    private String message;
    private static final int NOT_LOADED = -1;
    private static final int NO_REPEAT = 0;
    private static final int REPEAT_CASE_ONE = 1;
    private static final int REPEAT_CASE_TWO = 2;

    /**
     *
     * @param inputCommand
     * @param returnValue
     * @param fileType
     */
    public RepeatResult(final InputCommands inputCommand, final int returnValue,
                        final String fileType) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();

        String repeatMode = null;
        if (returnValue == NO_REPEAT) {
            repeatMode = "no repeat.";
        } else if (returnValue == REPEAT_CASE_ONE) {
            if (fileType.equals("playlist")) {
                repeatMode = "repeat all.";
            } else {
                repeatMode = "repeat once.";
            }
        } else if (returnValue == REPEAT_CASE_TWO) {
            if (fileType.equals("playlist")) {
                repeatMode = "repeat current song.";
            } else {
                repeatMode = "repeat infinite.";
            }
        }

        if (returnValue == NOT_LOADED) {
            this.message = "Please load a source before setting the repeat status.";
        } else {
            this.message = (new StringBuilder()).append("Repeat mode changed to ").
                    append(repeatMode).toString();
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
