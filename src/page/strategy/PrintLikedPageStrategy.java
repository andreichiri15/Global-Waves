package page.strategy;

import library.User;

public class PrintLikedPageStrategy implements PrintPageStrategy {
    private User currentUserLoaded;

    public PrintLikedPageStrategy(final User currentUserLoaded) {
        this.currentUserLoaded = currentUserLoaded;
    }

    /**
     * method that creates the string associated with liked page
     *
     * @return the liked page string
     */
    public String printPage() {
        StringBuilder result = new StringBuilder();
        result.append("Liked songs:\n\t[");
        for (int i = 0; i < currentUserLoaded.getLikedSongs().size(); i++) {
            result.append(currentUserLoaded.getLikedSongs().get(i).getName() + " - "
                    + currentUserLoaded.getLikedSongs().get(i).getOwner());

            if (i < currentUserLoaded.getLikedSongs().size() - 1) {
                result.append(", ");
            }
        }

        result.append("]\n\nFollowed playlists:\n\t[");
        for (int i = 0; i < currentUserLoaded.getFollowedPlaylists().size(); i++) {
            result.append(currentUserLoaded.getFollowedPlaylists().get(i).getName() + " - "
                    + currentUserLoaded.getFollowedPlaylists().get(i).getOwner());

            if (i < currentUserLoaded.getFollowedPlaylists().size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
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
