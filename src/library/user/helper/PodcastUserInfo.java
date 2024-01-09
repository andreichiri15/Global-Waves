package library.user.helper;

public class PodcastUserInfo {
    private String podcastName;
    private int currentId;
    private int currentTime;

    public PodcastUserInfo(final String name, final int currentId, final int currentTime) {
        this.podcastName = name;
        this.currentId = currentId;
        this.currentTime = currentTime;
    }

    /**
     *
     * @return
     */
    public String getPodcastName() {
        return podcastName;
    }

    /**
     *
     * @param podcastName
     */
    public void setPodcastName(final String podcastName) {
        this.podcastName = podcastName;
    }

    /**
     *
     * @return
     */
    public int getCurrentId() {
        return currentId;
    }

    /**
     *
     * @param currentId
     */
    public void setCurrentId(final int currentId) {
        this.currentId = currentId;
    }

    /**
     *
     * @return
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     *
     * @param currentTime
     */
    public void setCurrentTime(final int currentTime) {
        this.currentTime = currentTime;
    }
}
