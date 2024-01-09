package library.fields;

import library.filetypes.Playlist;

import java.util.ArrayList;

public class PlaylistFields {
    private String name;
    private ArrayList<String> songs;
    private String visibility;
    private int followers;

    /**
     *
     * @param playlist
     */
    public PlaylistFields(final Playlist playlist) {
        this.name = playlist.getName();
        this.songs = new ArrayList<>();
        for (int i = 0; i < playlist.getSongList().size(); i++) {
            this.songs.add(playlist.getSongList().get(i).getName());
        }
        if (playlist.isVisible()) {
            this.visibility = "public";
        } else {
            this.visibility = "private";
        }
        this.followers = playlist.getFollowers();
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }

    /**
     *
     * @return
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     *
     * @param visibility
     */
    public void setVisibility(final String visibility) {
        this.visibility = visibility;
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
}
