package library.user.helper.wrapped;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;

public class WrappedStatsHost extends WrappedStats {
    private HashMap<String, Integer> topEpisodes;
    private int listeners;
    private HashMap<String, Integer> listenersHash;

    public WrappedStatsHost() {
        super();
        topEpisodes = new HashMap<>();
        listeners = 0;
        listenersHash = new HashMap<>();
    }

    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
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
