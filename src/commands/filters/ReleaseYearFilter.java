package commands.filters;

public class ReleaseYearFilter {
    private char comparison;
    private int year;

    /**
     * Constructs a ReleaseYearFilter object based on the specified criteria.
     *
     * @param criteria A string representing the criteria for filtering release years.
     *                 The first character represents the comparison operator ('<' or '>').
     *                 The remaining characters represent the base year for the comparison.
     */
    public ReleaseYearFilter(final String criteria) {
        this.comparison = criteria.charAt(0);
        this.year = Integer.parseInt(criteria.substring(1));
    }

    /**
     * Checks if the current year is greater than the specified base year.
     *
     * @param baseYear The base year for comparison.
     * @return {@code true} if the current year is greater than the base year;
     *         {@code false} otherwise.
     */
    public boolean greaterThan(final int baseYear) {
        if (comparison == '>' && baseYear > this.year) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the current year is lower than the specified base year.
     *
     * @param baseYear The base year for comparison.
     * @return {@code true} if the current year is lower than the base year;
     *         {@code false} otherwise.
     */
    public boolean lowerThan(final int baseYear) {
        if (comparison == '<' && baseYear < this.year) {
            return true;
        }

        return false;
    }

    /**
     *
     * @return
     */
    public char getComparison() {
        return comparison;
    }

    /**
     *
     * @param comparison
     */
    public void setComparison(final char comparison) {
        this.comparison = comparison;
    }

    /**
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(final int year) {
        this.year = year;
    }
}
