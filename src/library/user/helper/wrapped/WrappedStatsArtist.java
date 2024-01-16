package library.user.helper.wrapped;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;

public class WrappedStatsArtist extends WrappedStats {
    private int listeners;
    private HashMap<String, Integer> topSongs;
    private HashMap<String, Integer> topAlbums;
    private ArrayList<String> topFans;
    private HashMap<String, Integer> listenersHash;

    public WrappedStatsArtist() {
        super();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topFans = new ArrayList<>();
        listenersHash = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public int getListeners() {
        return listeners;
    }

    /**
     *
     * @param listeners
     */
    public void setListeners(final int listeners) {
        this.listeners = listeners;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    /**
     *
     * @param topSongs
     */
    public void setTopSongs(final HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    /**
     *
     * @param topAlbums
     */
    public void setTopAlbums(final HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getTopFans() {
        return topFans;
    }

    /**
     *
     * @param topFans
     */
    public void setTopFans(final ArrayList<String> topFans) {
        this.topFans = topFans;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public HashMap<String, Integer> getListenersHash() {
        return listenersHash;
    }

    /**
     *
     * @param listenersHash
     */
    @JsonIgnore
    public void setListenersHash(final HashMap<String, Integer> listenersHash) {
        this.listenersHash = listenersHash;
    }
}
