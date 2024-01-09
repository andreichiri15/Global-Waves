package commands.filters;

import java.util.ArrayList;

public class SongFilter extends Filter {
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;

    /**
     *
     * @param allfilter
     */
    public SongFilter(final AllFilters allfilter) {
        this.name = allfilter.getName();
        this.album = allfilter.getAlbum();
        this.tags = allfilter.getTags();
        this.lyrics = allfilter.getLyrics();
        this.genre = allfilter.getGenre();
        this.releaseYear = allfilter.getReleaseYear();
        this.artist = allfilter.getArtist();
    }

    /**
     *
     * @return
     */
    public String getAlbum() {
        return album;
    }

    /**
     *
     * @param album
     */
    public void setAlbum(final String album) {
        this.album = album;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     */
    public void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     */
    public String getLyrics() {
        return lyrics;
    }

    /**
     *
     * @param lyrics
     */
    public void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    /**
     *
     * @return
     */
    public String getGenre() {
        return genre;
    }

    /**
     *
     * @param genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     *
     * @return
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     *
     * @param releaseYear
     */
    public void setReleaseYear(final String releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     *
     * @return
     */
    public String getArtist() {
        return artist;
    }

    /**
     *
     * @param artist
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

}
