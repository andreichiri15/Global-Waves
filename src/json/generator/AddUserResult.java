package json.generator;

import commands.InputCommands;

public class AddUserResult extends Result {
    private String user;
    private String message;
    private static final int NOT_TAKEN = 0;

    public AddUserResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (returnValue == NOT_TAKEN) {
            this.message = "The username " + inputCommand.getUsername()
                    + " has been added successfully.";
        } else {
            this.message = "The username " + inputCommand.getUsername() + " is already taken.";
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
