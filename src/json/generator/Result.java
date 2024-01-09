package json.generator;

public class Result {
    protected String command;
    protected int timestamp;

    /**
     *
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     *
     * @return
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
}
