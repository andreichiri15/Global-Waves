package page.strategy;

import library.User;
import library.filetypes.Playlist;
import library.filetypes.Song;

import java.util.ArrayList;
import java.util.Comparator;

public class PrintHomePageStrategy implements PrintPageStrategy {
    private User currentUserLoaded;
    public PrintHomePageStrategy(final User currentUserLoaded) {
        this.currentUserLoaded = currentUserLoaded;
    }

    /**
     * method that creates the string associated with home page
     *
     * @return the home page string
     */
    public String printPage() {
        ArrayList<String> likedSongs = new ArrayList<>();
        ArrayList<String> followedPlaylists = new ArrayList<>();
        ArrayList<String> recommendedSongs = new ArrayList<>();
        ArrayList<String> recommendedPlaylists = new ArrayList<>();

        class SongComparator implements Comparator<Song> {
            public int compare(final Song song1, final Song song2) {
                return Integer.compare(song2.getNrLikes(), song1.getNrLikes());
            }
        }

        ArrayList<Song> sortedLikedSongs = new ArrayList<>(currentUserLoaded.getLikedSongs());
        sortedLikedSongs.sort(new SongComparator());

        for (int i = 0; i < sortedLikedSongs.size() && i < COUNTMAX; i++) {
            likedSongs.add(sortedLikedSongs.get(i).getName());
        }

        class PlaylistComparator implements Comparator<Playlist> {
            public int compare(final Playlist playlist1, final Playlist playlist2) {
                return Integer.compare(playlist2.getTotalLikes(), playlist1.getTotalLikes());
            }
        }

        ArrayList<Playlist> sortedPlaylists = new ArrayList<>(currentUserLoaded.
                getFollowedPlaylists());
        sortedPlaylists.sort(new PlaylistComparator());

        for (int i = 0; i < sortedPlaylists.size() && i < COUNTMAX; i++) {
            followedPlaylists.add(sortedPlaylists.get(i).getName());
        }

        for (int i = 0; i < currentUserLoaded.getRecommendedSongs().size()
                && i < COUNTMAX; i++) {
            recommendedSongs.add(currentUserLoaded.getRecommendedSongs().get(i).getName());
        }

        for (int i = 0; i < currentUserLoaded.getRecommendedPlaylists().size()
                && i < COUNTMAX; i++) {
            recommendedPlaylists.add(currentUserLoaded.getRecommendedPlaylists().get(i).getName());
        }

        StringBuilder result = new StringBuilder();
        result.append("Liked songs:\n\t[");
        result = formatArrayList(likedSongs, result);
        result.append("\n\nFollowed playlists:\n\t[");
        result = formatArrayList(followedPlaylists, result);
        result.append("\n\nSong recommendations:\n\t[");
        result = formatArrayList(recommendedSongs, result);
        result.append("\n\nPlaylists recommendations:\n\t[");
        result = formatArrayList(recommendedPlaylists, result);


        return result.toString();
    }

    /**
     * method that appends to the given result StringBuilder the elements of the given list
     *
     * @param list given list
     * @param result given StringBuilder
     * @return the StringBuilder after adding elements from list to result
     */
    public StringBuilder formatArrayList(final ArrayList<String> list,
                                         final StringBuilder result) {
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i < list.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result;
    }

    /**
     *
     * @return
     */
    public User getCurrentUserLoaded() {
        return currentUserLoaded;
    }

    /**
     *
     * @param currentUserLoaded
     */
    public void setCurrentUserLoaded(final User currentUserLoaded) {
        this.currentUserLoaded = currentUserLoaded;
    }
}
