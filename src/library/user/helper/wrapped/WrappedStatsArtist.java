package library.user.helper.wrapped;

public class WrappedStatsArtist extends WrappedStats {
    private int listenedCount;

    public WrappedStatsArtist() {
        super();
        listenedCount = 0;
    }

    public int getListenedCount() {
        return listenedCount;
    }

    public void setListenedCount(int listenedCount) {
        this.listenedCount = listenedCount;
    }
}
