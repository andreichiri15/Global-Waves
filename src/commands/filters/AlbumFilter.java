package commands.filters;

public class AlbumFilter extends Filter {
    private String owner;
    private String description;

    public AlbumFilter(final AllFilters allFilter) {
        this.name = allFilter.getName();
        this.owner = allFilter.getOwner();
        this.description = allFilter.getDescription();
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(final String owner) {
        this.owner = owner;
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
}
