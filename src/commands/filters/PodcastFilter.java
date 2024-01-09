package commands.filters;

public class PodcastFilter extends Filter {
    private String owner;

    public PodcastFilter(final AllFilters allfilter) {
        this.name = allfilter.getName();
        this.owner = allfilter.getOwner();
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
}
