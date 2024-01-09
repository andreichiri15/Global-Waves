package library.filetypes;

import commands.InputCommands;

import java.util.ArrayList;

public class Playlist extends AudioFile {
    private ArrayList<Song> songList;
    private int playlistId;
    private boolean isVisible;
    private int followers;

    /**
     *
     */
    public Playlist() {

    }

    /**
     *
     * @param inputCommand
     */
    public Playlist(final InputCommands inputCommand) {
        this.name = inputCommand.getPlaylistName();
        this.isVisible = true;
        this.songList = new ArrayList();
        this.owner = inputCommand.getUsername();
    }

    /**
     * calculates the total number of all songs contained in the playlist
     * @return the total number of likes
     */
    public int getTotalLikes() {
        int totalLikes = 0;
        for (int i = 0; i < songList.size(); i++) {
            totalLikes += songList.get(i).getNrLikes();
        }

        return totalLikes;
    }

    /**
     *
     * @return
     */
    public ArrayList<Song> getSongList() {
        return songList;
    }

    /**
     *
     * @param songList
     */
    public void setSongList(final ArrayList<Song> songList) {
        this.songList = songList;
    }

    /**
     *
     * @return
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     *
     * @return
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     *
     * @param visible
     */
    public void setVisible(final boolean visible) {
        isVisible = visible;
    }

    /**
     *
     * @return
     */
    public int getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     */
    public void setFollowers(final int followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(final String owner) {
        this.owner = owner;
    }
}
