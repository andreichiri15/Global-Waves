package json.generator;

import commands.InputCommands;
import library.fields.PodcastFields;

import java.util.ArrayList;

public class ShowPodcastResult extends Result {
    private String user;
    private ArrayList<PodcastFields> result;

    /**
     *
     * @param inputCommand
     * @param result
     */
    public ShowPodcastResult(final InputCommands inputCommand,
                             final ArrayList<PodcastFields> result) {
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        this.command = inputCommand.getCommand();
        this.result = result;
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
    public ArrayList<PodcastFields> getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final ArrayList<PodcastFields> result) {
        this.result = result;
    }
}
