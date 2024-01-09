package json.generator;

import commands.InputCommands;
import utils.Errors;

public class ForwardBackwardResult extends Result {
    private String user;
    private String message;
    private static final int IS_FORWARD = 0;
    private static final int IS_BACKWARD = 1;


    /**
     *
     * @param inputCommand
     * @param returnValue
     * @param state
     */
    public ForwardBackwardResult(final InputCommands inputCommand,
                                 final int returnValue, final int state) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == Errors.NOT_LOADED && state == IS_FORWARD) {
            this.message = "Please load a source before attempting to forward.";
        } else if (returnValue == Errors.NOT_LOADED && state == IS_BACKWARD) {
            this.message = "Please load a source before attempting to backward.";
        } else if (returnValue == Errors.NOT_PODCAST) {
            this.message = "The loaded source is not a podcast.";
        } else if (returnValue == Errors.OK && state == IS_FORWARD) {
            this.message = "Skipped forward successfully.";
        } else {
            this.message = "Rewound successfully.";
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
