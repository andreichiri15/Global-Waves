package json.generator;

import commands.InputCommands;
import library.fields.AlbumFields;

import java.util.ArrayList;

public class ShowAlbumResult extends Result {
    private String user;
    private ArrayList<AlbumFields> result;

    /**
     *
     * @param inputCommand
     * @param result
     */
    public ShowAlbumResult(final InputCommands inputCommand, final ArrayList<AlbumFields> result) {
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
    public ArrayList<AlbumFields> getResult() {
        return result;
    }

    /**
     *
     * @param result
     */
    public void setResult(final ArrayList<AlbumFields> result) {
        this.result = result;
    }
}
