package library.user.helper.wrapped;

import java.util.HashMap;

public class WrappedStatsUser extends WrappedStats {
    private HashMap<String, Integer> topSongs;
    private HashMap<String, Integer> topAlbums;
    private HashMap<String, Integer> topArtists;
    private HashMap<String, Integer> topGenres;
    private HashMap<String, Integer> topEpisodes;

    public WrappedStatsUser() {
        super();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topEpisodes = new HashMap<>();
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
     * @param listenedSongs
     */
    public void setTopSongs(final HashMap<String, Integer> listenedSongs) {
        this.topSongs = listenedSongs;
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
    public HashMap<String, Integer> getTopArtists() {
        return topArtists;
    }

    /**
     *
     * @param topArtists
     */
    public void setTopArtists(final HashMap<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Integer> getTopGenres() {
        return topGenres;
    }

    /**
     *
     * @param topGenres
     */
    public void setTopGenres(final HashMap<String, Integer> topGenres) {
        this.topGenres = topGenres;
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
}
