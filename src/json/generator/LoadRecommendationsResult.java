package json.generator;

import commands.InputCommands;
import utils.Errors;

public class LoadRecommendationsResult extends Result {
    private String user;
    private String message;

    public LoadRecommendationsResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();

        if (returnValue == Errors.USER_NOT_ONLINE) {
            this.message = user + " is offline.";
        } else if (returnValue == Errors.NO_NEW_RECOMMANDATION) {
            this.message = "No recommendations available.";
        } else {
            this.message = "Playback loaded successfully.";
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
