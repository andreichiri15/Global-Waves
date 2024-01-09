package commands.filters;

import java.util.ArrayList;

public class AllFilters {
    private String name;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    private String owner;
    private String description;

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

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }
}
