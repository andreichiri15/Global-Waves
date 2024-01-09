package library.filetypes;

import fileio.input.SongInput;

import java.util.ArrayList;

public class Song extends AudioFile {
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
//    private String artist;
    private int nrLikes;

    /**
     *
     * @param songInput
     */
    public Song(final SongInput songInput) {
        this.name = songInput.getName();
        this.duration = songInput.getDuration();
        this.album = songInput.getAlbum();
        this.tags = songInput.getTags();
        this.lyrics = songInput.getLyrics();
        this.genre = songInput.getGenre();
        this.releaseYear = songInput.getReleaseYear();
//        this.artist = songInput.getArtist();
        this.owner = songInput.getArtist();
    }

    /**
     * checks whether the song is in given album
     * @param givenAlbum given album
     * @return true if the song exists in album, false otherwise
     */
    public boolean isSongInAlbum(final Album givenAlbum) {
        for (int i = 0; i < givenAlbum.getSongs().size(); i++) {
            if (this == givenAlbum.getSongs().get(i)) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @return
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
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
    public Integer getReleaseYear() {
        return releaseYear;
    }

    /**
     *
     * @param releaseYear
     */
    public void setReleaseYear(final Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

//    /**
//     *
//     * @return
//     */
//    public String getArtist() {
//        return artist;
//    }
//
//    /**
//     *
//     * @param artist
//     */
//    public void setArtist(final String artist) {
//        this.artist = artist;
//    }

    /**
     *
     * @return
     */
    public int getNrLikes() {
        return nrLikes;
    }

    /**
     *
     * @param nrLikes
     */
    public void setNrLikes(final int nrLikes) {
        this.nrLikes = nrLikes;
    }
}
