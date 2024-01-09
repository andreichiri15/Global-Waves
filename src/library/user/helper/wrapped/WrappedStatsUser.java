package library.user.helper.wrapped;

import library.filetypes.Song;

import java.util.HashMap;

public class WrappedStatsUser extends WrappedStats {
    HashMap<String, Integer> topSongs;
    HashMap<String, Integer> topAlbums;
    HashMap<String, Integer> topArtists;
    HashMap<String, Integer> topGenres;
    HashMap<String, Integer> topEpisodes;

    public WrappedStatsUser() {
        super();
        topSongs = new HashMap<>();
        topAlbums = new HashMap<>();
        topArtists = new HashMap<>();
        topGenres = new HashMap<>();
        topEpisodes = new HashMap<>();
    }

    public HashMap<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(HashMap<String, Integer> listenedSongs) {
        this.topSongs = listenedSongs;
    }

    public HashMap<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(HashMap<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public HashMap<String, Integer> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(HashMap<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    public HashMap<String, Integer> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(HashMap<String, Integer> topGenres) {
        this.topGenres = topGenres;
    }

    public HashMap<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(HashMap<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }
}
