package json.generator;

import commands.InputCommands;

public class WrappedNoDataResult extends Result {
    private String user;
    private String message;

    public WrappedNoDataResult(final InputCommands inputCommand, final String userType) {
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.command = inputCommand.getCommand();
        if (userType.equals("user")) {
            this.message = "No data to show for user " + user + ".";
        } else if (userType.equals("artist")) {
            this.message = "No data to show for artist " + user + ".";
        } else {
            this.message = "No data to show for host " + user + ".";
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
