package json.generator;

import library.filetypes.Song;
import commands.InputCommands;

import java.util.ArrayList;

public class PreferredSongsResult extends Result {
    private String user;
    private ArrayList<String> result;

    /**
     *
     * @param inputCommand
     * @param preferredSongs
     */
    public PreferredSongsResult(final InputCommands inputCommand,
                                final ArrayList<Song> preferredSongs) {
        this.command = inputCommand.getCommand();
        this.user = inputCommand.getUsername();
        this.timestamp = inputCommand.getTimestamp();
        result = new ArrayList<>();
        for (int i = 0; i < preferredSongs.size(); i++) {
            result.add(preferredSongs.get(i).getName());
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
    public ArrayList<String> getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final ArrayList<String> result) {
        this.result = result;
    }
}
