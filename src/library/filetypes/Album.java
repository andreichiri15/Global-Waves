package library.filetypes;

import commands.InputCommands;

import java.util.ArrayList;

public class Album extends AudioFile {
    private String name;
//    private String owner;
    private String description;
    private ArrayList<Song> songs;

    public Album(final InputCommands inputCommand, final ArrayList<Song> newSongs) {
        this.name = inputCommand.getName();
        this.owner = inputCommand.getUsername();
        this.description = inputCommand.getDescription();
        this.songs = newSongs;
    }

    /**
     * calculates the number of total likes of all songs contained in album
     * @return the total number of likes
     */
    public int getTotalLikes() {
        int sumLikes = 0;
        for (int i = 0; i < songs.size(); i++) {
            sumLikes += songs.get(i).getNrLikes();
        }

        return sumLikes;
    }

    /**
     * method that checks whether the album contains a song with given name
     * @param songName the name of the song
     * @return true if album contains song, false otherwise
     */
    public boolean doesContainSong(final String songName) {
        for (int i = 0; i < songs.size(); i++) {
            if (songName.equals(songs.get(i).getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * method that checks whether the album contains at least one song from given playlist
     * @param playlist given playlist
     * @return the index of song in playlist that is present in album
     */
    public int doesContainSongFromPlaylist(final Playlist playlist) {
        for (int i = 0; i < playlist.getSongList().size(); i++) {
            if (doesContainSong(playlist.getSongList().get(i).getName())) {
                return i;
            }
        }

        return -1;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    @Override
    public void setName(final String name) {
        this.name = name;
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

    /**
     *
     * @return
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }
}
