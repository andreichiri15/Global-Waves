package page;

import library.User;
import page.strategy.Context;
import page.strategy.PrintArtistPageStrategy;
import page.strategy.PrintHomePageStrategy;
import page.strategy.PrintHostPageStrategy;
import page.strategy.PrintLikedPageStrategy;


public class Page {
    private String currentPageType;
    private User currentUserLoaded;
    private static final int COUNTMAX = 5;

    public Page(final User currentUserLoaded, final String pageType) {
        this.currentPageType = pageType;
        this.currentUserLoaded = currentUserLoaded;
    }

    /**
     * method that handles the print current page command
     *
     * @return the string correspondent to the current page we want to print
     */
    public String printCurrentPage() {
        Context context = new Context();

        switch (currentPageType) {
            case "Home" -> context.setStrategy(new PrintHomePageStrategy(currentUserLoaded));
            case "LikedContent" -> context.
                    setStrategy(new PrintLikedPageStrategy(currentUserLoaded));
            case "Artist" -> context.setStrategy(new PrintArtistPageStrategy(currentUserLoaded));
            case "Host" -> context.setStrategy(new PrintHostPageStrategy(currentUserLoaded));
            default -> System.out.println("Not a valid page");
        }

        return context.executeStrategy();
    }

    /**
     *
     * @return
     */
    public String getCurrentPageType() {
        return currentPageType;
    }

    /**
     *
     * @param currentPageType
     */
    public void setCurrentPageType(final String currentPageType) {
        this.currentPageType = currentPageType;
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
