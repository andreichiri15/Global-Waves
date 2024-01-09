package library.fields;

import library.filetypes.Album;

import java.util.ArrayList;

public class AlbumFields {
    private String name;
    private ArrayList<String> songs;

    public AlbumFields(final Album album) {
        this.name = album.getName();
        songs = new ArrayList<>();
        for (int i = 0; i < album.getSongs().size(); i++) {
            songs.add(album.getSongs().get(i).getName());
        }
    }

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
    public ArrayList<String> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }
}
