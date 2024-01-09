package page.strategy;

import library.User;

import java.util.ArrayList;

public class PrintArtistPageStrategy implements PrintPageStrategy {
    private User currentUserLoaded;

    public PrintArtistPageStrategy(final User currentUserLoaded) {
        this.currentUserLoaded = currentUserLoaded;
    }

    /**
     * method that creates the string associated with artist page
     *
     * @return the artist page string
     */
    public String printPage() {
        ArrayList<String> albumList = new ArrayList<>();

        for (int i = 0; i < currentUserLoaded.getAlbums().size(); i++) {
            albumList.add(currentUserLoaded.getAlbums().get(i).getName());
        }

        StringBuilder result = new StringBuilder();
        result.append("Albums:\n\t[");
        result = formatArrayList(albumList, result);
        result.append("\n\nMerch:\n\t[");
        for (int i = 0; i < currentUserLoaded.getMerchItems().size(); i++) {
            result.append(currentUserLoaded.getMerchItems().get(i).getName() + " - "
                    + currentUserLoaded.getMerchItems().get(i).getPrice() + ":\n\t"
                    + currentUserLoaded.getMerchItems().get(i).getDescription());

            if (i < currentUserLoaded.getMerchItems().size() - 1) {
                result.append(", ");
            }
        }
        result.append("]\n\nEvents:\n\t[");
        for (int i = 0; i < currentUserLoaded.getEvents().size(); i++) {
            result.append(currentUserLoaded.getEvents().get(i).getName() + " - "
                    + currentUserLoaded.getEvents().get(i).getDateString() + ":\n\t"
                    + currentUserLoaded.getEvents().get(i).getDescription());

            if (i < currentUserLoaded.getEvents().size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
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
