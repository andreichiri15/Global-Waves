package library.filetypes;

public class AudioFile {
    protected String name;
    protected String owner;

    /**
     *
     */
    public AudioFile() {

    }

    /**
     *
     * @param name
     */
    public AudioFile(final String name) {
        this.name = name;
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
