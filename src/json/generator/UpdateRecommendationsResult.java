package json.generator;

import commands.InputCommands;
import utils.Errors;

public class UpdateRecommendationsResult extends Result {
    private String user;
    private String message;

    public UpdateRecommendationsResult(final InputCommands inputCommand, final int returnValue) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();

        if (returnValue == Errors.USER_NOT_EXIST) {
            this.message = "The username " + user + " doesn't exist.";
        } else if (returnValue == Errors.USER_NOT_NORMAL) {
            this.message = user + " is not a normal user.";
        } else if (returnValue == Errors.NO_NEW_RECOMMANDATION) {
            this.message = "No new recommendations were found";
        } else {
            this.message = "The recommendations for user " + user
                    + " have been updated successfully.";
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
