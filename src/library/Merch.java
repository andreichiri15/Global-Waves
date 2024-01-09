package library;

import commands.InputCommands;

public class Merch {
    private String name;
    private String description;
    private int price;

    public Merch(final InputCommands inputCommand) {
        this.name = inputCommand.getName();
        this.description = inputCommand.getDescription();
        this.price = inputCommand.getPrice();
    }

    /**
     *
     * @return
     */
    public boolean isPriceValid() {
        return price > 0;
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
    public int getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(final int price) {
        this.price = price;
    }
}
