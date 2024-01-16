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

    /**
     *
     * @return
     */
    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    /**
     *
     * @param topEpisodes
     */
    public void setTopEpisodes(final HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
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
