package json.generator;

import library.filetypes.Playlist;
import library.fields.PlaylistFields;
import commands.InputCommands;

import java.util.ArrayList;

public class ShowPlayResult extends Result {
    private String user;
    private ArrayList<PlaylistFields> result;

    /**
     *
     * @param inputCommands
     * @param playlists
     */
    public ShowPlayResult(final InputCommands inputCommands, final ArrayList<Playlist> playlists) {
        this.command = inputCommands.getCommand();
        this.user = inputCommands.getUsername();
        this.timestamp = inputCommands.getTimestamp();

        result = new ArrayList<>();
        for (int i = 0; i < playlists.size(); i++) {
            result.add(new PlaylistFields(playlists.get(i)));
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
    public ArrayList<PlaylistFields> getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final ArrayList<PlaylistFields> result) {
        this.result = result;
    }
}
