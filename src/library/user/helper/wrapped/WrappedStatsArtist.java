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

    public int getListeners() {
        return listeners;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }

    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(HashMap<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public ArrayList<String> getTopFans() {
        return topFans;
    }

    public void setTopFans(ArrayList<String> topFans) {
        this.topFans = topFans;
    }

    @JsonIgnore
    public HashMap<String, Integer> getListenersHash() {
        return listenersHash;
    }

    @JsonIgnore
    public void setListenersHash(HashMap<String, Integer> listenersHash) {
        this.listenersHash = listenersHash;
    }
}
