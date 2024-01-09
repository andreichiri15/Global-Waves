package json.generator;

import library.User;
import library.filetypes.AudioFile;
import commands.InputCommands;

import java.util.ArrayList;

public class SearchResult extends Result {
    private String user;
    private String message;
    private ArrayList<String> results;

    /**
     *
     * @param inputCommand
     * @param results
     */
    public SearchResult(final InputCommands inputCommand, final ArrayList<AudioFile> results,
                        final int type) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (results == null) {
            this.message = inputCommand.getUsername() + " is offline.";
            this.results = new ArrayList<>();
        } else {
            this.message = "Search returned " + results.size() + " results";
            ArrayList<String> resultNames = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                resultNames.add(results.get(i).getName());
            }
            this.results = resultNames;
        }
    }

    /**
     *
     * @param inputCommand
     * @param results
     */
    public SearchResult(final InputCommands inputCommand, final ArrayList<User> results) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        if (results == null) {
            this.message = inputCommand.getUsername() + " is offline.";
            this.results = new ArrayList<>();
        } else {
            this.message = "Search returned " + results.size() + " results";
            ArrayList<String> resultNames = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                resultNames.add(results.get(i).getUsername());
            }
            this.results = resultNames;
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

    /**
     *
     * @return
     */
    public ArrayList<String> getResults() {
        return results;
    }

    /**
     *
     * @param results
     */
    public void setResults(final ArrayList<String> results) {
        this.results = results;
    }
}
