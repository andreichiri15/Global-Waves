package page.strategy;

import fileio.input.EpisodeInput;
import library.User;

public class PrintHostPageStrategy implements PrintPageStrategy {
    private User currentUserLoaded;

    public PrintHostPageStrategy(final User currentUserLoaded) {
        this.currentUserLoaded = currentUserLoaded;
    }

    /**
     * method that creates the string associated with host page
     *
     * @return the host page string
     */
    public String printPage() {
        StringBuilder result = new StringBuilder();

        result.append("Podcasts:\n\t[");
        for (int i = 0; i < currentUserLoaded.getPodcasts().size(); i++) {
            result.append(currentUserLoaded.getPodcasts().get(i).getName() + ":\n\t[");
            for (int j = 0; j < currentUserLoaded.getPodcasts().get(i).getEpisodes().size(); j++) {
                EpisodeInput currEp = currentUserLoaded.getPodcasts().get(i).getEpisodes().get(j);
                result.append(currEp.getName() + " - " + currEp.getDescription());

                if (j < currentUserLoaded.getPodcasts().get(i).getEpisodes().size() - 1) {
                    result.append(", ");
                }
            }

            result.append("]\n");

            if (i < currentUserLoaded.getPodcasts().size() - 1) {
                result.append(", ");
            }
        }

        result.append("]\n\nAnnouncements:\n\t[");
        for (int i = 0; i < currentUserLoaded.getEvents().size(); i++) {
            result.append(currentUserLoaded.getEvents().get(i).getName() + ":\n\t"
                    + currentUserLoaded.getEvents().get(i).getDescription());

            if (i < currentUserLoaded.getEvents().size() - 1) {
                result.append(", ");
            }
        }
        result.append("\n]");

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
