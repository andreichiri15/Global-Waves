package library;

import commands.InputCommands;

public class Event {
    private String name;
    private String description;
    private String dateString;
    private static final int MAX_DAYS = 31;
    private static final int MAX_MONTHS = 12;
    private static final int MAX_DAYS_FEBRUARY = 28;
    private static final int FEBRUARY = 2;
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2023;

    /**
     *
     * @param inputCommands
     */
    public Event(final InputCommands inputCommands) {
        this.name = inputCommands.getName();
        this.description = inputCommands.getDescription();
        this.dateString = inputCommands.getDate();
    }

    /**
     * method that verifies whether the date given as string is valid or not
     * @return true is the date is valid, false otherwise
     */
    public boolean isDateValid() {
        String[] dateParts = dateString.split("-");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        if (day > MAX_DAYS) {
            return false;
        }

        if (month == FEBRUARY && day > MAX_DAYS_FEBRUARY) {
            return false;
        }

        if (month > MAX_MONTHS) {
            return false;
        }

        if (year < MIN_YEAR || year > MAX_YEAR) {
            return false;
        }

        return true;
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
    public String getDateString() {
        return dateString;
    }

    /**
     *
     * @param dateString
     */
    public void setDateString(final String dateString) {
        this.dateString = dateString;
    }
}
