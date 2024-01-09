package library.filetypes;

import fileio.input.EpisodeInput;

public class Episode {
    private String name;
    private Integer duration;
    private String description;

    /**
     *
     * @param episodeInput
     */
    public Episode(final EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        this.description = episodeInput.getDescription();
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
    public Integer getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(final Integer duration) {
        this.duration = duration;
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
