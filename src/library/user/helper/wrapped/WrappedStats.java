package library.user.helper.wrapped;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WrappedStats {
    protected boolean isLoaded;

    public WrappedStats() {
        isLoaded = false;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     *
     * @param loaded
     */
    public void setLoaded(final boolean loaded) {
        isLoaded = loaded;
    }
}
