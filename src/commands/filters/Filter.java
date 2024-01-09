package commands.filters;

public class Filter {
    protected String name;

    public Filter() {

    }
    public Filter(final AllFilters allfilter) {
        this.name = allfilter.getName();
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
}
