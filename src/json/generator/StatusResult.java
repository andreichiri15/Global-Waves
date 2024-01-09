package json.generator;

import library.fields.StatusFields;
import commands.InputCommands;

public class StatusResult extends Result {
    private String user;
    private StatusFields stats;

    /**
     *
     * @param inputCommand
     * @param statusFields
     */
    public StatusResult(final InputCommands inputCommand, final StatusFields statusFields) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.stats = statusFields;
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
    public StatusFields getStats() {
        return stats;
    }

    /**
     *
     * @param stats
     */
    public void setStats(final StatusFields stats) {
        this.stats = stats;
    }
}
