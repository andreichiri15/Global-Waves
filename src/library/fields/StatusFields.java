package library.fields;

public class StatusFields {
    private String name;
    private int remainedTime;
    private String repeat;
    private boolean shuffle;
    private boolean paused;

    /**
     *
     * @param name
     * @param remainedTime
     * @param repeat
     * @param shuffle
     * @param paused
     */
    public StatusFields(final String name, final int remainedTime, final String repeat,
                        final boolean shuffle, final boolean paused) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.repeat = repeat;
        this.shuffle = shuffle;
        this.paused = paused;
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
    public int getRemainedTime() {
        return remainedTime;
    }

    /**
     *
     * @param remainedTime
     */
    public void setRemainedTime(final int remainedTime) {
        this.remainedTime = remainedTime;
    }

    /**
     *
     * @return
     */
    public String getRepeat() {
        return repeat;
    }

    /**
     *
     * @param repeat
     */
    public void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    /**
     *
     * @return
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     *
     * @param shuffle
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    /**
     *
     * @return
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     *
     * @param paused
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }
}
