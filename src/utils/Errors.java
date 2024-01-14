package utils;

public final class Errors {
    public static final int OK = 0;
    public static final int USER_NOT_EXIST = -4;
    public static final int PLAYLIST_NOT_EXIST = -3;
    public static final int NOT_SONG = -6;
    public static final int NOT_PLAYLIST = -6;
    public static final int NOT_PODCAST = -6;
    public static final int NOT_LOADED = -5;
    public static final int SOURCE_NOT_SELECTED = -8;
    public static final int OWN_PLAYLIST = -7;
    public static final int NOT_ARTIST = -3;
    public static final int ALBUM_SAME_NAME = -2;
    public static final int DUPLICATE_SONG = -1;
    public static final int EVENT_DUPLICATE = -2;
    public static final int DATE_INVALID = -1;
    public static final int MERCH_DUPLICATE = -2;
    public static final int PRICE_NOT_VALID = -1;
    public static final int NOT_HOST = -3;
    public static final int DUPLICATE_PODCAST = -2;
    public static final int DUPLICATE_EPISODE = -1;
    public static final int EVENT_NOT_EXIST = -1;
    public static final int ALBUM_NOT_EXIST = -2;
    public static final int ALBUM_CANT_DELETE = -1;
    public static final int PODCAST_NOT_EXIST = -2;
    public static final int PODCAST_LOADED = -1;
    public static final int USER_NOT_ONLINE = -10;
    public static final int USER_NOT_NORMAL = -3;
    public static final int USER_IN_ACTION = -1;
    public static final int PAGE_NOT_CORRESPONDENT = -3;
    public static final int HAS_SUBSCRIBED = 1;
    public static final int HAS_UNSUBSCRIBED = 2;
    public static final int HISTORY_EMPTY_PREV = -1;
    public static final int SUCCES_PREV = 1;
    public static final int HISTORY_EMPTY_NEXT = -2;
    public static final int SUCCES_NEXT = 2;

    private Errors() {
        throw new UnsupportedOperationException("This is a utility class and "
                + "cannot be instantiated");
    }
}
