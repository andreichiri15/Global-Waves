package json.generator;

import commands.InputCommands;

public class SwitchVisibilityResult extends Result {
    private String user;
    private String message;
    private static final int ID_TOO_HIGH = -1;
    private static final int PRIVATE_OK = 0;

    /**
     *
     * @param inputCommand
     * @param returnValue
     */
    public SwitchVisibilityResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == ID_TOO_HIGH) {
            this.message = "The specified playlist ID is too high.";
        } else if (returnValue == PRIVATE_OK) {
            this.message = "Visibility status updated successfully to private.";
        } else {
            this.message = "Visibility status updated successfully to public.";
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
