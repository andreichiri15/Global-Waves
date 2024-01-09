package json.generator;

import library.User;
import commands.InputCommands;

public class SelectResult extends Result {
    private String user;
    private String message;
    private static final int NOT_SEARCH = -2;
    private static final int ID_TOO_HIGH = -1;
    private static final int USER_SELECT = -3;

    /**
     *
     * @param inputCommands
     * @param index
     * @param user
     */
    public SelectResult(final InputCommands inputCommands, final int index, final User user) {
        this.command = inputCommands.getCommand();
        this.user = inputCommands.getUsername();
        this.timestamp = inputCommands.getTimestamp();

        if (index == NOT_SEARCH) {
            this.message = "Please conduct a search before making a selection.";
        } else if (index == ID_TOO_HIGH) {
            this.message = "The selected ID is too high.";
        } else if (index == USER_SELECT) {
            this.message = "Successfully selected " + user.getSearchBar().getSelectedUser().
                    getUsername() + "'s page.";
        } else {
            this.message = "Successfully selected " + user.getSearchBar().getSelectedAudioFile().
                    getName() + ".";
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
