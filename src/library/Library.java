package library;

import commands.InputCommands;
import library.filetypes.Album;
import library.filetypes.Song;
import library.filetypes.AudioFile;
import library.filetypes.Podcast;
import library.filetypes.Playlist;

import library.user.helper.PodcastUserInfo;
import library.user.helper.RevenueStats;
import library.user.helper.wrapped.WrappedStatsArtist;
import library.user.helper.wrapped.WrappedStatsHost;
import library.user.helper.wrapped.WrappedStatsUser;

import java.util.*;

public class Library {
    static ArrayList<Song> songs;
    static ArrayList<Podcast> podcasts;
    static ArrayList<User> users;
    static final AudioFile NULL_AUDIO_FILE = new AudioFile("");
    static ArrayList<Playlist> playlistList;
    static ArrayList<Album> albumList;
    private static final int COUNTMAX = 5;
    private static Library instance = null;

    /**
     * constructor for library
     * @param songs
     * @param podcasts
     * @param users
     */
    public Library(final ArrayList<Song> songs, final ArrayList<Podcast> podcasts,
                   final ArrayList<User> users) {
        this.songs = songs;
        this.podcasts = podcasts;
        this.users = users;
        playlistList = new ArrayList<>();
        albumList = new ArrayList<>();
    }

    /**
     * method that searches through the library of users until it finds a user with
     * the same username
     *
     * @param username
     * @return
     */
    public User getUserByUsername(final String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }

        return null;
    }

    /**
     * method that updates the state of every single player of every user after every command
     *
     * @param addedTime is the time passed from the last command
     */
    public void updatePlayerAllUsers(final int addedTime, final int timestamp) {
        for (int i = 0; i < users.size(); i++) {
            Player player = users.get(i).getPlayer();

            if (users.get(i).isOnline() && player != null && !player.isPaused()) {
                player.setCurrentTime(player.getCurrentTime() + addedTime);

                if (player.getFileType().equals("song")) {
                    Song song = (Song) player.getAudioFile();

                    while (player.getCurrentTime() > song.getDuration()) {
                        int currentTime = player.getCurrentTime();
                        int songDuration = song.getDuration();
                        if (player.getRepeatState() == 0) {
                            player.setCurrentTime(0);
                            player.setPaused(true);
                            player.setAudioFile(NULL_AUDIO_FILE);
                        } else if (player.getRepeatState() == 1) {
                            player.setCurrentTime(currentTime - songDuration);
                            player.setRepeatState(0);
                        } else {
                            player.setCurrentTime(currentTime - songDuration);
                        }
                    }
                } else if (player.getFileType().equals("podcast")) {
                    Podcast podcast = (Podcast) player.getAudioFile();
                    while (player.getCurrentTime() >= podcast.getEpisodes().get(player.getCurrentId()).getDuration()) {
                        int currentTime = player.getCurrentTime();
                        int episodeDuration = podcast.getEpisodes().get(player.getCurrentId()).
                                getDuration();
                        player.setCurrentTime(currentTime - episodeDuration);

                        player.setCurrentId(player.getCurrentId() + 1);

                        if (player.getCurrentId() == podcast.getEpisodes().size()) {
                            continue;
                        }

                        updatePodcastWrappedStats(users.get(i), podcast, player, player.getCurrentId());
                    }

                    if (player.getCurrentId() == podcast.getEpisodes().size()) {
                        player.setCurrentId(0);
                    }

                    users.get(i).getPodcastInfoMap().put(player.getAudioFile().getName(),
                            new PodcastUserInfo(player.getAudioFile().getName(),
                                    player.getCurrentId(), player.getCurrentTime()));
                } else if (player.getFileType().equals("playlist")) {
                    Playlist playlist = (Playlist) (player.getAudioFile());

                    while (!playlist.getSongList().isEmpty() && player.getCurrentId() < playlist.
                            getSongList().size() && player.getCurrentTime() > playlist.
                            getSongList().get(player.getCurrentId()).getDuration()) {
                        int currentTime = player.getCurrentTime();
                        int songDuration = playlist.getSongList().get(player.getCurrentId()).
                                getDuration();

                        player.setCurrentTime(currentTime - songDuration);

                        if (player.getRepeatState() == 2) {
                            continue;
                        }

                        if (player.isShuffle()) {
                            player.setShuffleId(player.getShuffleId() + 1);

                            if (player.getShuffleId() < player.getShuffledIndexes().size()) {
                                int newId = player.getShuffledIndexes().get(player.
                                        getShuffleId());
                                player.setCurrentId(newId);
                            } else {
                                player.setCurrentId(player.getShuffledIndexes().size());
                            }
                        } else {
                            player.setCurrentId(player.getCurrentId() + 1);
                        }

                        if (player.getCurrentId() == playlist.getSongList().size()
                                && player.getRepeatState() == 0) {
                            player.setCurrentTime(0);
                            player.setCurrentId(0);
                            player.setAudioFile(Library.NULL_AUDIO_FILE);
                            player.setPaused(true);
                            player.setShuffle(false);
                        }

                        if (player.getCurrentId() == playlist.getSongList().size()
                                && player.getRepeatState() == 1) {
                            if (player.isShuffle()) {
                                player.setShuffleId(0);
                                player.setCurrentId(player.getShuffledIndexes().get(0));
                            } else {
                                player.setCurrentId(0);
                            }

                            player.setCurrentTime(currentTime - songDuration);
                        }
                    }
                } else if (player.getFileType().equals("album")) {
                    Album album = (Album) (player.getAudioFile());

                    while (player.getCurrentTime() >= album.getSongs().get(player.
                            getCurrentId()).getDuration()) {
                        int currentTime = player.getCurrentTime();
                        int songDuration = album.getSongs().get(player.getCurrentId()).
                                getDuration();

                        player.setCurrentTime(currentTime - songDuration);

                        if (player.getRepeatState() == 2) {
                            continue;
                        }

                        if (player.isShuffle()) {
                            player.setShuffleId(player.getShuffleId() + 1);

                            if (player.getShuffleId() < player.getShuffledIndexes().size()) {
                                int newId = player.getShuffledIndexes().get(player.
                                        getShuffleId());
                                player.setCurrentId(newId);
                            } else {
                                player.setCurrentId(player.getShuffledIndexes().size());
                            }
                        } else {
                            player.setCurrentId(player.getCurrentId() + 1);
                        }

                        if (player.getCurrentId() == album.getSongs().size()
                                && player.getRepeatState() == 0) {
                            player.setCurrentTime(0);
                            player.setCurrentId(0);
                            player.setAudioFile(Library.NULL_AUDIO_FILE);
                            player.setPaused(true);
                            player.setShuffle(false);
                        }

                        if (player.getCurrentId() == album.getSongs().size()
                                && player.getRepeatState() == 1) {
                            if (player.isShuffle()) {
                                player.setShuffleId(0);
                                player.setCurrentId(player.getShuffledIndexes().get(0));
                            } else {
                                player.setCurrentId(0);
                            }

                            player.setCurrentTime(currentTime - songDuration);
                        }

                        if (player.getAudioFile().equals(Library.NULL_AUDIO_FILE)) {
                            continue;
                        }

                        updateAlbumWrappedStats(users.get(i), album, player);
                    }
                }
            }

        }
    }

    public void updatePodcastWrappedStats(final User currUser, final Podcast podcast,
                                          final Player player, final int currentId) {
        WrappedStatsUser wrappedStatsUser = (WrappedStatsUser) currUser.
                getStats();

        int listenedTimes = wrappedStatsUser.getTopEpisodes().getOrDefault(podcast.getEpisodes().
                get(currentId).getName(), 0);
        wrappedStatsUser.getTopEpisodes().put(podcast.getEpisodes().get(currentId).getName(),
                listenedTimes + 1);

        User host = getUserByUsername(podcast.getOwner());
        if (host == null) {
            return;
        }

        WrappedStatsHost wrappedStatsHost = (WrappedStatsHost) host.getStats();

        listenedTimes = wrappedStatsHost.getTopEpisodes().getOrDefault(podcast.getEpisodes().
                get(currentId).getName(), 0);
        wrappedStatsHost.getTopEpisodes().put(podcast.getEpisodes().get(currentId).getName(),
                listenedTimes + 1);

        listenedTimes = wrappedStatsHost.getListenersHash().getOrDefault(currUser.getUsername(), 0);
        wrappedStatsHost.getListenersHash().put(currUser.getUsername(), listenedTimes + 1);
    }

    public void updateAlbumWrappedStats(final User currUser, final Album album,
                                        final Player player) {
        WrappedStatsUser wrappedStatsUser = (WrappedStatsUser) currUser.
                getStats();
        int listenedTimes = wrappedStatsUser.getTopAlbums().getOrDefault(album.getName(), 0);
        wrappedStatsUser.getTopAlbums().put(album.getName(), listenedTimes + 1);

        listenedTimes = wrappedStatsUser.getTopArtists().getOrDefault(album.getOwner(), 0);
        wrappedStatsUser.getTopArtists().put(album.getOwner(), listenedTimes + 1);

        listenedTimes = wrappedStatsUser.getTopSongs().getOrDefault(album.getSongs().get(
                    player.getCurrentId()).getName(), 0);
        wrappedStatsUser.getTopSongs().put(album.getSongs().get(player.
                getCurrentId()).getName(), listenedTimes + 1);

        listenedTimes = wrappedStatsUser.getTopGenres().getOrDefault(album.getSongs().get(
                    player.getCurrentId()).getGenre(), 0);
        wrappedStatsUser.getTopGenres().put(album.getSongs().get(player.
                getCurrentId()).getGenre(), listenedTimes + 1);

        User artist = getUserByUsername(album.getOwner());

        WrappedStatsArtist wrappedStatsArtist = (WrappedStatsArtist) artist.
                getStats();

        listenedTimes = wrappedStatsArtist.getTopAlbums().getOrDefault(album.getName(), 0);
        wrappedStatsArtist.getTopAlbums().put(album.getName(), listenedTimes + 1);

        listenedTimes = wrappedStatsArtist.getTopSongs().getOrDefault(album.getSongs().get(
                    player.getCurrentId()).getName(), 0);
        wrappedStatsArtist.getTopSongs().put(album.getSongs().get(player.
                getCurrentId()).getName(), listenedTimes + 1);

        listenedTimes = wrappedStatsArtist.getListenersHash().
                getOrDefault(currUser.getUsername(), 0);
        wrappedStatsArtist.getListenersHash().put(currUser.getUsername(), listenedTimes + 1);
    }

    /**
     * get total likes of all songs made by artist
     * @param user artist
     * @return total likes
     */
    public int getAllLikesForUser(final User user) {
        int totalLikes = 0;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getOwner().equals(user.getUsername())) {
                totalLikes += songs.get(i).getNrLikes();
            }
        }

        return totalLikes;
    }

    /**
     * method that handles all getTop5 commands
     *
     * @param state represents whether the command is Top5Playlists / Top5LikedSongs / Top5Artists
     *              / Top5Albums etc.
     * @return the list of top 5 songs / playlists / artists / albums etc.
     */
    public ArrayList<String> getTop5(final int state) {
        ArrayList<String> result = new ArrayList<>();

        if (state == 0) {
            ArrayList<Song> copySongList = new ArrayList<>(songs);

            class SongComparator implements Comparator<Song> {
                public int compare(final Song song1, final Song song2) {
                    return Integer.compare(song2.getNrLikes(), song1.getNrLikes());
                }
            }
            copySongList.sort(new SongComparator());

            for (int i = 0; i < copySongList.size() && i < COUNTMAX; i++) {
                result.add(copySongList.get(i).getName());
            }
        } else if (state == 1) {
            ArrayList<Playlist> copyPlaylistList = new ArrayList<Playlist>(playlistList);

            class PlaylistComparator implements Comparator<Playlist> {

                public int compare(final Playlist playlist1, final Playlist playlist2) {
                    return Integer.compare(playlist2.getFollowers(), playlist1.getFollowers());
                }
            }
            copyPlaylistList.sort(new PlaylistComparator());

            int count = 0;
            for (int i = 0; i < copyPlaylistList.size() && count < COUNTMAX; i++) {
                result.add(copyPlaylistList.get(i).getName());
                count++;
            }
        } else if (state == 2) {
            ArrayList<Album> copyAlbumList = new ArrayList<>(albumList);

            class AlbumComparator implements Comparator<Album> {
                public int compare(final Album album1, final Album album2) {
                    int returnCompare = Integer.compare(album2.getTotalLikes(),
                            album1.getTotalLikes());
                    if (returnCompare == 0) {
                        return album1.getName().compareTo(album2.getName());
                    }
                    return returnCompare;
                }
            }
            copyAlbumList.sort(new AlbumComparator());

            int count = 0;
            for (int i = 0; i < copyAlbumList.size() && count < COUNTMAX; i++) {
                result.add(copyAlbumList.get(i).getName());
                count++;
            }
        } else {
            ArrayList<User> artists = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserType().equals("artist")) {
                    artists.add(users.get(i));
                }
            }

            class ArtistComparator implements Comparator<User> {
                public int compare(final User user1, final User user2) {
                    return Integer.compare(getAllLikesForUser(user2), getAllLikesForUser(user1));
                }
            }

            artists.sort(new ArtistComparator());

            int count = 0;
            for (int i = 0; i < artists.size() && count < COUNTMAX; i++) {
                result.add(artists.get(i).getUsername());
                count++;
            }
        }

        return result;
    }

    /**
     * method that returns a list of all online users
     * @return list of online users' usernames
     */
    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> usernameList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isOnline() && users.get(i).getUserType().equals("user")) {
                usernameList.add(users.get(i).getUsername());
            }
        }
        return usernameList;
    }

    /**
     * method that returns all users added so far
     * @return a list of all users' usernames
     */
    public ArrayList<String> getAllUsers() {
        ArrayList<String> usernameList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("user")) {
                usernameList.add(users.get(i).getUsername());
            }
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("artist")) {
                usernameList.add(users.get(i).getUsername());
            }
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("host")) {
                usernameList.add(users.get(i).getUsername());
            }
        }

        return usernameList;
    }

    /**
     * method that a new user in library
     * @param inputCommand input command
     * @return error number that each represent a different type of error
     */
    public int addUser(final InputCommands inputCommand) {
        for (int i = 0; i < users.size(); i++) {
            if (inputCommand.getUsername().equals(users.get(i).getUsername())) {
                return -1;
            }
        }

        users.add(new User(inputCommand));

        return 0;
    }

    /**
     * method that checks whether we can delete a given user from the database
     * in practice, whether another user is currently interacting with the user or not
     * @param myUser the user we want to delete
     * @return true if we can delete the user, false otherwise
     */
    public boolean isUserAvailableForDelete(final User myUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(myUser)) {
                continue;
            }

            if (users.get(i).getPlayer() != null && users.get(i).getPlayer().getAudioFile()
                    != Library.NULL_AUDIO_FILE && users.get(i).getPlayer().getAudioFile().
                    getOwner().equals(myUser.getUsername())) {

                return false;
            }

            if (users.get(i).getCurrentPage().getCurrentUserLoaded().equals(myUser)) {

                return false;
            }

            if (users.get(i).getSearchBar() != null && users.get(i).getSearchBar().
                    getSearchedUsers() != null) {
                for (int j = 0; j < users.get(i).getSearchBar().getSearchedUsers().size(); j++) {
                    if (users.get(i).getSearchBar().getSearchedUsers().get(j).getUsername().
                            equals(myUser.getUsername())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * method that deletes every trace of existence, specifically for artists
     * in practice, we delete this user's songs from database, other users' playlists and
     * liked songs etc.
     * @param myUser the user whose traces we want to delete
     */
    public void deleteTracesArtist(final User myUser) {
        if (!myUser.getUserType().equals("artist")) {
            return;
        }

        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getOwner().equals(myUser.getUsername())) {
                songs.remove(i);
                i--;
            }
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) == myUser) {
                continue;
            }

            for (int j = 0; j < users.get(i).getLikedSongs().size(); j++) {
                if (users.get(i).getLikedSongs().get(j).getOwner().equals(myUser.getUsername())) {
                    users.get(i).getLikedSongs().remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < playlistList.size(); i++) {
            for (int j = 0; j < playlistList.get(i).getSongList().size(); j++) {
                if (playlistList.get(i).getSongList().get(j).getOwner().
                        equals(myUser.getUsername())) {
                    playlistList.get(i).getSongList().remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getOwner().equals(myUser.getUsername())) {
                albumList.remove(i);
                i--;
            }
        }
    }

    /**
     * method that deletes every trace of a host's existence
     * in practice, we delete the user's podcasts from the library
     * @param myUser the user whose traces we want to delete
     */
    public void deleteTracesHost(final User myUser) {
        if (!myUser.getUserType().equals("host")) {
            return;
        }
        for (int i = 0; i < Library.podcasts.size(); i++) {
            if (Library.podcasts.get(i).getOwner().equals(myUser.getUsername())) {
                Library.podcasts.remove(i);
                i--;
            }
        }
    }

    /**
     * method which deletes the traces of any type of user's existence
     * in practice, we revoke the likes given to songs, the follows to playlists etc,
     * @param myUser
     */
    public void deleteTracesEveryUser(final User myUser) {
        for (int i = 0; i < myUser.getFollowedPlaylists().size(); i++) {
            int currentFollowers = myUser.getFollowedPlaylists().get(i).getFollowers();
            myUser.getFollowedPlaylists().get(i).setFollowers(currentFollowers - 1);
        }

        for (int i = 0; i < myUser.getLikedSongs().size(); i++) {
            int currentNrLikes = myUser.getLikedSongs().get(i).getNrLikes();
            myUser.getLikedSongs().get(i).setNrLikes(currentNrLikes - 1);
        }


        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(myUser)) {
                continue;
            }

            for (int j = 0; j < users.get(i).getFollowedPlaylists().size(); j++) {
                if (users.get(i).getFollowedPlaylists().get(j).getOwner().
                        equals(myUser.getUsername())) {
                    users.get(i).getFollowedPlaylists().remove(j);
                    j--;
                }
            }
        }
    }
    /**
     * method that tries to delete a user from library, doing so only if other users are
     * not interacting with the given user at that moment in time
     * @param myUser
     * @return
     */
    public int deleteUser(final User myUser) {
        if (!isUserAvailableForDelete(myUser)) {
            return -1;
        }

        deleteTracesArtist(myUser);
        deleteTracesHost(myUser);
        deleteTracesEveryUser(myUser);

        users.remove(myUser);

        return 0;
    }

    public static HashMap<String, RevenueStats> sortByRevenue(HashMap<String, RevenueStats> statsHash) {
        List<Map.Entry<String, RevenueStats>> entryList = new ArrayList<>(statsHash.entrySet());

        entryList.sort((entry1, entry2) -> {
            int rankingComparison = Double.compare(entry1.getValue().getMerchRevenue()
                    + entry1.getValue().getSongRevenue(), entry2.getValue().getMerchRevenue()
                    + entry2.getValue().getSongRevenue());

            // If the ranking values are equal, compare lexicographically by key
            if (rankingComparison == 0) {
                return entry2.getKey().compareTo(entry1.getKey());
            }

            return rankingComparison;
        });

        HashMap<String, RevenueStats> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, RevenueStats> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public HashMap<String, RevenueStats> endProgram() {
        HashMap<String, RevenueStats> artistsStatsMap = new HashMap<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserType().equals("artist") && users.get(i).getRevenueStats().
                    isArtistLoaded()) {
                artistsStatsMap.put(users.get(i).getUsername(), users.get(i).getRevenueStats());
            }
        }

        HashMap<String, RevenueStats> sortedStatsMap = sortByRevenue(artistsStatsMap);

        int ranking = sortedStatsMap.size();
        for (Map.Entry<String, RevenueStats> entry : sortedStatsMap.entrySet()) {
            entry.getValue().setRanking(ranking--);
        }

        return sortedStatsMap;
    }

    /**
     *
     * @return
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     *
     * @return
     */
    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     *
     * @param podcasts
     */
    public void setPodcasts(final ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    /**
     *
     * @return
     */
    public static ArrayList<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     */
    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }
}
