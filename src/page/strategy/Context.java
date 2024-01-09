package page.strategy;

public class Context {
    private PrintPageStrategy strategy;

    /**
     * executes given previously in setter
     * @return
     */
    public String executeStrategy() {
        return strategy.printPage();
    }

    /**
     *
     * @return
     */
    public PrintPageStrategy getStrategy() {
        return strategy;
    }

    /**
     *
     * @param strategy
     */
    public void setStrategy(final PrintPageStrategy strategy) {
        this.strategy = strategy;
    }
}
