package page.strategy;

public interface PrintPageStrategy {
    int COUNTMAX = 5;

    /**
     * the method for printing current page
     *
     * @return string specific to each page
     */
    String printPage();
}
