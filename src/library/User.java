package library;

import commands.InputCommands;
import fileio.input.UserInput;
import library.fields.AlbumFields;
import library.fields.PodcastFields;
import library.filetypes.Album;
import library.filetypes.Playlist;
import library.filetypes.Podcast;
import library.filetypes.Song;
import library.user.helper.PodcastUserInfo;
import library.user.helper.RevenueStats;
import library.user.helper.notifications.Observable;
import library.user.helper.notifications.Observer;
import library.user.helper.wrapped.WrappedStats;
import library.user.helper.wrapped.WrappedStatsArtist;
import library.user.helper.wrapped.WrappedStatsHost;
import library.user.helper.wrapped.WrappedStatsUser;
import page.Page;
import page.command.ChangePageCommand;
import page.command.CommandManager;
import utils.Errors;

import library.user.helper.notifications.*;
import java.util.*;

public class User implements Observer, Observable {
    private String username;
    private int age;
    private String city;
    private Player player;
    private SearchBar searchBar;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> playlists;
    private ArrayList<Playlist> followedPlaylists;
    private int lastPodcastCurrentTime;
    private int lastPodcastId;
    private boolean isOnline;
    private String userType;
    private ArrayList<Album> albums;
    private Page currentPage;
    private ArrayList<Event> events;
    private ArrayList<Merch> merchItems;
    private ArrayList<Podcast> podcasts;
    private HashMap<String, PodcastUserInfo> podcastInfoMap;
    private WrappedStats wrappedStatsUser;
    private ArrayList<Observer> subscribers;
    private ArrayList<Notification> notifications;
    private CommandManager commandManager;
    private ArrayList<Song> recommendedSongs;
    private ArrayList<Playlist> recommendedPlaylists;
    private ArrayList<Merch> boughtMerch;
    private RevenueStats revenueStats;

    /**
     *
     * @param userInput
     */
    public User(final UserInput userInput) {
        this.age = userInput.getAge();
        this.city = userInput.getCity();
        this.username = userInput.getUsername();
        this.playlists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.isOnline = true;
        this.userType = "user";
        this.currentPage = new Page(this, "Home");
        this.podcastInfoMap = new HashMap<>();
        this.wrappedStatsUser = new WrappedStatsUser();
        this.notifications = new ArrayList<>();
        this.commandManager = new CommandManager();
        this.recommendedSongs = new ArrayList<>();
        this.recommendedPlaylists = new ArrayList<>();
        this.boughtMerch = new ArrayList<>();
    }

    public User(final InputCommands inputCommand) {
        this.age = inputCommand.getAge();
        this.city = inputCommand.getCity();
        this.username = inputCommand.getUsername();
        this.userType = inputCommand.getType();
        this.isOnline = true;
        this.playlists = new ArrayList<>();
        this.likedSongs = new ArrayList<>();
        this.followedPlaylists = new ArrayList<>();
        this.currentPage = new Page(this, "Home");
        if (userType.equals("user")) {
            this.podcastInfoMap = new HashMap<>();
            this.wrappedStatsUser = new WrappedStatsUser();
            this.notifications = new ArrayList<>();
            this.commandManager = new CommandManager();
            this.recommendedSongs = new ArrayList<>();
            this.recommendedPlaylists = new ArrayList<>();
            this.boughtMerch = new ArrayList<>();
        } else if (userType.equals("artist")) {
            this.albums = new ArrayList<>();
            this.events = new ArrayList<>();
            this.merchItems = new ArrayList<>();
            this.wrappedStatsUser = new WrappedStatsArtist();
            this.subscribers = new ArrayList<>();
            this.revenueStats = new RevenueStats();
        } else if (userType.equals("host")) {
            this.podcasts = new ArrayList<>();
            this.events = new ArrayList<>();
            this.wrappedStatsUser = new WrappedStatsHost();
            this.subscribers = new ArrayList<>();
        }
    }

    /**
     *
     * @param observer
     */
    public void addSubscriber(final Observer observer) {
        subscribers.add(observer);
    }

    /**
     *
     * @param observer
     */
    public void removeSubscriber(final Observer observer) {
        subscribers.remove(observer);
    }

    /**
     *
     * @param message
     */
    public void notifySubscribers(final String name, final String message) {
        for (Observer subscriber : subscribers) {
            subscriber.update(name, message);
        }
    }

    @Override
    public void update(final String name, final String description) {
        Notification notification = new Notification(name, description);
        notifications.add(notification);
    }

    public int subscribe(final InputCommands inputCommand, final Library library) {
        if (!currentPage.getCurrentPageType().equals("Artist") && !currentPage.getCurrentPageType().equals("Host")) {
            return Errors.PAGE_NOT_CORRESPONDENT;
        }

        if (currentPage.getCurrentUserLoaded().subscribers.contains(this)) {
            currentPage.getCurrentUserLoaded().subscribers.remove(this);
            return Errors.HAS_UNSUBSCRIBED;
        }

        currentPage.getCurrentUserLoaded().addSubscriber(this);

        return Errors.HAS_SUBSCRIBED;
    }

    public ArrayList<Song> getSongsWithGivenGenre(final String genre) {
        ArrayList<Song> songsWithGenre = new ArrayList<>();
        for (int i = 0; i < Library.songs.size(); i++) {
            if (Library.songs.get(i).getGenre().equals(genre)) {
                songsWithGenre.add(Library.songs.get(i));
            }
        }

        return songsWithGenre;
    }

    public int updateRecommendations(final InputCommands inputCommand, final Library library) {
        if (!userType.equals("user")) {
            return Errors.USER_NOT_NORMAL;
        }

        if (inputCommand.getRecommendationType().equals("random_song")) {
            int timePassed = player.getCurrentTime();
            if (timePassed < 30) {
                return Errors.NO_NEW_RECOMMANDATION;
            }

            Song currentSong = (Song)player.getAudioFile();
            ArrayList<Song> songsWithGenre = getSongsWithGivenGenre(currentSong.getGenre());

            Random random = new Random(timePassed);
            int randomIndex = random.nextInt(songsWithGenre.size());

            recommendedSongs.add(songsWithGenre.get(randomIndex));
        } else if (inputCommand.getRecommendationType().equals("fans_playlist")) {
            Playlist playlist = new Playlist();
            String currentPlaylistArtist = player.getAudioFile().getOwner();

            playlist.setName(currentPlaylistArtist + " Fan Club recommendations");

            recommendedPlaylists.add(playlist);
        } else {
            Playlist playlist = new Playlist();

            playlist.setName(username + "'s recommendations");

            recommendedPlaylists.add(playlist);
        }


        return 0;
    }

    public int buyMerch(final InputCommands inputCommand) {
        if (!currentPage.getCurrentPageType().equals("Artist")) {
            return Errors.PAGE_NOT_CORRESPONDENT;
        }

        for (int i = 0; i < currentPage.getCurrentUserLoaded().merchItems.size(); i++) {
            if (currentPage.getCurrentUserLoaded().merchItems.get(i).getName().equals(inputCommand.getName())) {
                boughtMerch.add(currentPage.getCurrentUserLoaded().merchItems.get(i));
                currentPage.getCurrentUserLoaded().getRevenueStats().setArtistLoaded(true);

                Double merchRevenue = currentPage.getCurrentUserLoaded().getRevenueStats().
                        getMerchRevenue();
                currentPage.getCurrentUserLoaded().getRevenueStats().setMerchRevenue(merchRevenue
                        + currentPage.getCurrentUserLoaded().merchItems.get(i).getPrice());
                return 0;
            }
        }

        return Errors.MERCH_NOT_EXIST;
    }

    public ArrayList<String> seeMerch() {
        ArrayList<String> merchNames = new ArrayList<>();
        for (int i = 0; i < boughtMerch.size(); i++) {
            merchNames.add(boughtMerch.get(i).getName());
        }
        return merchNames;
    }

    /**
     * method that handles the 'create playlist' command
     *
     * @param inputCommand
     * @param library
     * @return a number referring to an error, similar to other methods
     */
    public int createPlaylist(final InputCommands inputCommand, final Library library) {
        Playlist playlist = new Playlist(inputCommand);
        for (int i = 0; i < playlists.size(); i++) {
            if (playlist.getName().equals(playlists.get(i).getName())) {
                return -1;
            }
        }
        playlists.add(playlist);
        Library.playlistList.add(playlist);
        return 0;
    }

    /**
     * method that handles the follow playlist command
     *
     * @return a number referring to an error / success of the command
     */
    public int followPlaylist() {
        if (searchBar == null || searchBar.getSelectedAudioFile() == null
                || searchBar.getSelectedAudioFile() == Library.NULL_AUDIO_FILE) {
            return Errors.SOURCE_NOT_SELECTED;
        }

        if (!searchBar.getType().equals("playlist")) {
            return Errors.NOT_PLAYLIST;
        }

        Playlist playlist = (Playlist) searchBar.getSelectedAudioFile();

        if (playlist.getOwner().equals(username)) {
            return Errors.OWN_PLAYLIST;
        }

        for (int i = 0; i < followedPlaylists.size(); i++) {
            if (playlist == followedPlaylists.get(i)) {
                playlist.setFollowers(playlist.getFollowers() - 1);
                followedPlaylists.remove(i);
                return 0;
            }
        }

        followedPlaylists.add(playlist);
        playlist.setFollowers(playlist.getFollowers() + 1);
        return 1;
    }

    /**
     * method that changes the visibility of the playlist selected
     *
     * @param playlistId the index of the playlist which the user wants to change its visibility
     * @return a number referring to an error
     * negative = couldn't execute the command
     * positive = otherwise
     */
    public int switchVisibility(final int playlistId) {
        if (playlistId > playlists.size()) {
            return -1;
        }

        playlists.get(playlistId - 1).setVisible(!playlists.get(playlistId - 1).isVisible());

        if (playlists.get(playlistId - 1).isVisible()) {
            return 1;
        }

        return 0;
    }

    /**
     * method that switches the connectivity of user
     *
     * @return an error number
     */
    public int switchConnectivity() {
        if (!userType.equals("user")) {
            return Errors.USER_NOT_NORMAL;
        }
        isOnline = !isOnline;

        return 0;
    }

    /**
     * method that adds a new album created by user after verifying if it is possible
     * to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int addAlbum(final InputCommands inputCommand) {
        if (!userType.equals("artist")) {
            return Errors.NOT_ARTIST;
        }

        for (int i = 0; i < albums.size(); i++) {
            if (albums.get(i).getName().equals(inputCommand.getName())) {
                return Errors.ALBUM_SAME_NAME;
            }
        }

        Set<String> songsSet = new HashSet<>();
        ArrayList<Song> newSongs = new ArrayList<>();

        for (int i = 0; i < inputCommand.getSongs().size(); i++) {
            if (!songsSet.add(inputCommand.getSongs().get(i).getName())) {
                return Errors.DUPLICATE_SONG;
            }

            newSongs.add(new Song(inputCommand.getSongs().get(i)));
        }

        Album newAlbum = new Album(inputCommand, newSongs);
        albums.add(newAlbum);
        Library.albumList.add(newAlbum);
        for (int i = 0; i < newSongs.size(); i++) {
            boolean existsInLibrary = false;
            for (int j = 0; j < Library.songs.size(); j++) {
                if (Library.songs.get(j).equals(newSongs.get(i))) {
                    existsInLibrary = true;
                    break;
                }
            }

            if (!existsInLibrary) {
                Library.songs.add(newSongs.get(i));
            }
        }

        notifySubscribers("New Album", "New Album from " + username + ".");
        return 0;
    }

    /**
     * method that returns a list of all albums made by artist
     *
     * @return a list of albums
     */
    public ArrayList<AlbumFields> showAlbums() {
        ArrayList<AlbumFields> albumFieldsList = new ArrayList<>();
        for (int i = 0; i < albums.size(); i++) {
            albumFieldsList.add(new AlbumFields(albums.get(i)));
        }
        return albumFieldsList;
    }

    /**
     * method that returns a list of all podcasts made by host
     *
     * @return a list of podcasts
     */
    public ArrayList<PodcastFields> showPodcasts() {
        ArrayList<PodcastFields> podcastFieldsList = new ArrayList<>();
        for (int i = 0; i < podcasts.size(); i++) {
            podcastFieldsList.add(new PodcastFields(podcasts.get(i)));
        }

        return podcastFieldsList;
    }

    /**
     * method that handles addEvent command which adds a new event by user after verifying if
     * it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int addEvent(final InputCommands inputCommand) {
        if (!userType.equals("artist")) {
            return Errors.NOT_ARTIST;
        }

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getName().equals(inputCommand.getName())) {
                return Errors.EVENT_DUPLICATE;
            }
        }

        Event newEvent = new Event(inputCommand);

        if (!newEvent.isDateValid()) {
            return Errors.DATE_INVALID;
        }

        events.add(newEvent);
        notifySubscribers("New Event", "New Event from " + username + ".");
        return 0;
    }

    /**
     * method that handles addMerch command which adds new merch by artist after verifying if
     * it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int addMerch(final InputCommands inputCommand) {
        if (!userType.equals("artist")) {
            return Errors.NOT_ARTIST;
        }

        for (int i = 0; i < merchItems.size(); i++) {
            if (inputCommand.getName().equals(merchItems.get(i).getName())) {
                return Errors.MERCH_DUPLICATE;
            }
        }

        Merch newMerchItem = new Merch(inputCommand);
        if (!newMerchItem.isPriceValid()) {
            return Errors.PRICE_NOT_VALID;
        }

        merchItems.add(newMerchItem);

        notifySubscribers("New Merchandise", "New Merchandise from " + username + ".");
        return 0;
    }

    /**
     * method that handles print current page command
     *
     * @return String of current page
     */
    public String printCurrentPage() {
        if (!isOnline) {
            return null;
        }
        return currentPage.printCurrentPage();
    }

    /**
     * method that adds a new podcast created by host after verifying if it is possible
     * to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int addPodcast(final InputCommands inputCommand) {
        if (!userType.equals("host")) {
            return Errors.NOT_HOST;
        }

        for (int i = 0; i < podcasts.size(); i++) {
            if (podcasts.get(i).getName().equals(inputCommand.getName())) {
                return Errors.DUPLICATE_PODCAST;
            }
        }

        Set<String> episodesSet = new HashSet<>();
        for (int i = 0; i < inputCommand.getEpisodes().size(); i++) {
            if (!episodesSet.add(inputCommand.getEpisodes().get(i).getName())) {
                return Errors.DUPLICATE_EPISODE;
            }
        }

        Podcast newPodcast = new Podcast(inputCommand);
        podcasts.add(newPodcast);
        Library.podcasts.add(newPodcast);

        notifySubscribers("New Podcast", "New Podcast from " + username + ".");
        return 0;
    }

    /**
     * method that handles addAnnouncement command which adds new announcement by host after
     * verifying if it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int addAnnouncement(final InputCommands inputCommand) {
        if (!userType.equals("host")) {
            return Errors.NOT_HOST;
        }

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getName().equals(inputCommand.getName())) {
                return Errors.EVENT_DUPLICATE;
            }
        }

        Event newAnnouncement = new Event(inputCommand);
        events.add(newAnnouncement);

        notifySubscribers("New Announcement", "New Announcement from " + username + ".");
        return 0;
    }

    /**
     * method that handles removeAnnouncement command which removes announcement by host after
     * verifying if it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int removeAnnouncement(final InputCommands inputCommand) {
        if (!userType.equals("host")) {
            return Errors.NOT_HOST;
        }

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getName().equals(inputCommand.getName())) {
                events.remove(i);
                return 0;
            }
        }

        return -1;
    }

    /**
     * method that changes the page of user to a new page given in the input command
     * @param inputCommand input command
     * @return error number
     */
    public int changePage(final InputCommands inputCommand, Library library) {
//        if (!inputCommand.getNextPage().equals("Home") && !inputCommand.getNextPage().
//                equals("LikedContent")) {
//            return -1;
//        }
        if (inputCommand.getNextPage().equals("Host")) {
            return -1;
        }

        User userLoaded = this;
        if (inputCommand.getNextPage().equals("Artist") || inputCommand.getNextPage().
                equals("Host")) {
            userLoaded = library.getUserByUsername(this.getPlayer().getAudioFile().getOwner());
        }

        currentPage = new Page(userLoaded, inputCommand.getNextPage());
        Page newPage = new Page(this, inputCommand.getNextPage());
        commandManager.changePage(new ChangePageCommand(this, newPage));

        return 0;
    }

    public int previousPage() {
        return commandManager.undo();
    }

    /**
     * method that deletes every trace of given album's existence
     *
     * @param album given album
     */
    public void deleteTracesAlbum(final Album album) {
        for (int i = 0; i < Library.users.size(); i++) {
            for (int j = 0; j < Library.users.get(i).getLikedSongs().size(); j++) {
                if (album.doesContainSong(Library.users.get(i).getLikedSongs().get(j).getName())) {
                    Library.users.get(i).getLikedSongs().remove(j);
                    j--;
                }
            }
        }

        for (int i = 0; i < Library.playlistList.size(); i++) {
            int returnIndex = album.doesContainSongFromPlaylist(Library.playlistList.get(i));
            if (returnIndex > 0) {
                Library.playlistList.remove(returnIndex);
            }
        }

        for (int i = 0; i < Library.songs.size(); i++) {
            if (Library.songs.get(i).isSongInAlbum(album)) {
                Library.songs.remove(i);
                i--;
            }
        }
    }

    /**
     * method that checks whether another user is currently interacting with given album
     *
     * @param album given album
     * @return true if another user is currently interacting with album, false otherwise
     */
    public boolean isAlbumLoaded(final Album album) {
        for (int i = 0; i < Library.users.size(); i++) {
            if (Library.users.get(i).getPlayer() == null || Library.users.get(i).getPlayer().
                    getFileType() == null) {
                continue;
            }

            if (Library.users.get(i).getPlayer().getFileType().equals("album")
                    && Library.users.get(i).getPlayer().getAudioFile().equals(album)) {
                return true;
            }

            if (Library.users.get(i).getPlayer().getFileType().equals("song")
                    && album.doesContainSong(Library.users.get(i).getPlayer().getAudioFile().
                            getName())) {
                return true;
            }

            if (Library.users.get(i).getPlayer().getFileType().equals("playlist")) {
                Playlist playlist = (Playlist) (Library.users.get(i).getPlayer().getAudioFile());

                for (int j = 0; j < playlist.getSongList().size(); j++) {
                    if (album.doesContainSong(playlist.getSongList().get(j).getName())) {
                        return true;
                    }
                }
                int currentId = Library.users.get(i).getPlayer().getCurrentId();
                Song currentSong = playlist.getSongList().get(currentId);
                for (int j = 0; j < album.getSongs().size(); j++) {
                    if (currentSong.equals(album.getSongs().get(j))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * method that handles removeAlbum command, which removes an album from host after verifying
     * whether it's possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int removeAlbum(final InputCommands inputCommand) {
        if (!userType.equals("artist")) {
            return Errors.NOT_ARTIST;
        }

        boolean found = false;
        int albumIndex = -1;
        for (int i = 0; i < albums.size() && !found; i++) {
            if (albums.get(i).getName().equals(inputCommand.getName())) {
                found = true;
                albumIndex = i;
            }
        }

        if (!found) {
            return Errors.ALBUM_NOT_EXIST;
        }

        if (isAlbumLoaded(albums.get(albumIndex))) {
            return Errors.ALBUM_CANT_DELETE;
        }

        deleteTracesAlbum(albums.get(albumIndex));
        albums.remove(albumIndex);

        return 0;
    }

    /**
     * method that checks whether another user is currently interacting with given podcast
     *
     * @param podcast given podcast
     * @return true if another user is currently interacting with podcast, false otherwise
     */
    public boolean isPodcastLoaded(final Podcast podcast) {
        for (int i = 0; i < Library.users.size(); i++) {
            if (Library.users.get(i).getPlayer() == null) {
                continue;
            }

            Player userPlayer = Library.users.get(i).getPlayer();

            if (userPlayer.getFileType() == null) {
                continue;
            }

            if (userPlayer.getFileType().equals("podcast")
                    && userPlayer.getAudioFile().equals(podcast)) {
                return true;
            }
        }

        return false;
    }

    /**
     * method that deletes all traces of podcast's existence
     * @param podcast given podcast
     */
    public void deleteTracesPodcast(final Podcast podcast) {
        Library.podcasts.remove(podcast);
    }

    /**
     * method that handles removePodcast command, which removes a podcast created by host after
     * verifying if it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int removePodcast(final InputCommands inputCommand) {
        if (!userType.equals("host")) {
            return Errors.NOT_HOST;
        }

        boolean found = false;
        int podcastIndex = -1;
        for (int i = 0; i < podcasts.size() && !found; i++) {
            if (inputCommand.getName().equals(podcasts.get(i).getName())) {
                found = true;
                podcastIndex = i;
            }
        }

        if (!found) {
            return Errors.PODCAST_NOT_EXIST;
        }

        if (isPodcastLoaded(podcasts.get(podcastIndex))) {
            return Errors.PODCAST_LOADED;
        }

        deleteTracesPodcast(podcasts.get(podcastIndex));
        podcasts.remove(podcastIndex);

        return 0;
    }

    /**
     * method that handles removeEvent command, which removes an event created by artist after
     * verifying if it is possible to do so
     *
     * @param inputCommand input command
     * @return error number
     */
    public int removeEvent(final InputCommands inputCommand) {
        if (!userType.equals("artist")) {
            return Errors.NOT_ARTIST;
        }

        boolean found = false;
        int eventId = -1;
        for (int i = 0; i < events.size() && !found; i++) {
            if (events.get(i).getName().equals(inputCommand.getName())) {
                found = true;
                eventId = i;
            }
        }

        if (!found) {
            return Errors.EVENT_NOT_EXIST;
        }

        events.remove(eventId);
        return 0;
    }

    /**
     * method that sorts a hashmap by its values
     * (took this from GeeksForGeeks)
     *
     * @param hm the hashmap I want sorted
     * @return the sorted hashmap
     */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>> (hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                int returnValue = (o2.getValue()).compareTo(o1.getValue());
                if (returnValue == 0) {
                    return (o1.getKey()).compareTo(o2.getKey());
                }
                return returnValue;
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> aa : list) {
            if (count == 5) {
                break;
            }
            temp.put(aa.getKey(), aa.getValue());
            count++;
        }
        return temp;
    }

    public WrappedStats getStats() {
        return wrappedStatsUser;
    }

    public WrappedStats getWrappedStats() {
        if (userType.equals("user")) {
            WrappedStatsUser userStats = (WrappedStatsUser) wrappedStatsUser;

            HashMap<String, Integer> topSongs = sortByValue(userStats.getTopSongs());
            HashMap<String, Integer> topAlbums = sortByValue(userStats.getTopAlbums());
            HashMap<String, Integer> topArtists = sortByValue(userStats.getTopArtists());
            HashMap<String, Integer> topGenres = sortByValue(userStats.getTopGenres());
            HashMap<String, Integer> topEpisodes = sortByValue(userStats.getTopEpisodes());

            WrappedStatsUser sortedWrappedStats = new WrappedStatsUser();
            sortedWrappedStats.setTopSongs(topSongs);
            sortedWrappedStats.setTopAlbums(topAlbums);
            sortedWrappedStats.setTopArtists(topArtists);
            sortedWrappedStats.setTopGenres(topGenres);
            sortedWrappedStats.setTopEpisodes(topEpisodes);
            return sortedWrappedStats;
        } else if (userType.equals("artist")) {
            WrappedStatsArtist artistStats = (WrappedStatsArtist) wrappedStatsUser;

            HashMap<String, Integer> topSongs = sortByValue(artistStats.getTopSongs());
            HashMap<String, Integer> topAlbums = sortByValue(artistStats.getTopAlbums());
            HashMap<String, Integer> listenersHash = sortByValue(artistStats.getListenersHash());

            WrappedStatsArtist sortedWrappedStats = new WrappedStatsArtist();
            sortedWrappedStats.setTopSongs(topSongs);
            sortedWrappedStats.setTopAlbums(topAlbums);
            sortedWrappedStats.setListeners(listenersHash.size());
            ArrayList<String> keySet = new ArrayList<>(listenersHash.keySet().stream().toList());
            sortedWrappedStats.setTopFans(keySet);
            return sortedWrappedStats;
        } else if (userType.equals("host")) {
            WrappedStatsHost hostStats = (WrappedStatsHost) wrappedStatsUser;

            HashMap<String, Integer> topPodcasts = sortByValue(hostStats.getTopEpisodes());
            HashMap<String, Integer> listenersHash = sortByValue(hostStats.getListenersHash());

            WrappedStatsHost sortedWrappedStats = new WrappedStatsHost();
            sortedWrappedStats.setTopEpisodes(topPodcasts);
            sortedWrappedStats.setListeners(listenersHash.size());
            return sortedWrappedStats;
        }

        return wrappedStatsUser;
    }

    /**
     *
     * @return
     */
    public ArrayList showPreferredSongs() {
        return likedSongs;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @param age
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(final Player player) {
        this.player = player;
    }

    /**
     *
     * @return
     */
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    /**
     *
     * @param likedSongs
     */
    public void setLikedSongs(final ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    /**
     *
     * @return
     */
    public SearchBar getSearchBar() {
        return searchBar;
    }

    /**
     *
     * @param searchBar
     */
    public void setSearchBar(final SearchBar searchBar) {
        this.searchBar = searchBar;
    }

    /**
     *
     * @return
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     *
     * @param playlists
     */
    public void setPlaylists(final ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    /**
     *
     * @return
     */
    public int getLastPodcastCurrentTime() {
        return lastPodcastCurrentTime;
    }

    /**
     *
     * @param lastPodcastCurrentTime
     */
    public void setLastPodcastCurrentTime(final int lastPodcastCurrentTime) {
        this.lastPodcastCurrentTime = lastPodcastCurrentTime;
    }

    /**
     *
     * @return
     */
    public int getLastPodcastId() {
        return lastPodcastId;
    }

    /**
     *
     * @param lastPodcastId
     */
    public void setLastPodcastId(final int lastPodcastId) {
        this.lastPodcastId = lastPodcastId;
    }

    /**
     *
     * @return
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /**
     *
     * @param followedPlaylists
     */
    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    /**
     *
     * @return
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     *
     * @param online
     */
    public void setOnline(final boolean online) {
        isOnline = online;
    }

    /**
     *
     * @return
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     */
    public void setUserType(final String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     *
     * @param albums
     */
    public void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     *
     * @return
     */
    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     */
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     *
     * @param events
     */
    public void setEvents(final ArrayList<Event> events) {
        this.events = events;
    }

    /**
     *
     * @return
     */
    public ArrayList<Merch> getMerchItems() {
        return merchItems;
    }

    /**
     *
     * @param merchItems
     */
    public void setMerchItems(final ArrayList<Merch> merchItems) {
        this.merchItems = merchItems;
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
    public HashMap<String, PodcastUserInfo> getPodcastInfoMap() {
        return podcastInfoMap;
    }

    /**
     *
     * @param podcastInfoMap
     */
    public void setPodcastInfoMap(final HashMap<String, PodcastUserInfo> podcastInfoMap) {
        this.podcastInfoMap = podcastInfoMap;
    }

    /**
     *
     * @return
     */
    public WrappedStats getWrappedStatsUser() {
        return wrappedStatsUser;
    }

    /**
     *
     * @param wrappedStatsUser
     */
    public void setWrappedStatsUser(final WrappedStatsUser wrappedStatsUser) {
        this.wrappedStatsUser = wrappedStatsUser;
    }

    /**
     *
     * @return
     */
    public ArrayList<Observer> getSubscribers() {
        return subscribers;
    }

    /**
     *
     * @param subscribers
     */
    public void setSubscribers(final ArrayList<Observer> subscribers) {
        this.subscribers = subscribers;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     *
     * @param notifications
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     *
     * @return
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     *
     * @param commandManager
     */
    public void setCommandManager(final CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     *
     * @return
     */
    public ArrayList<Song> getRecommendedSongs() {
        return recommendedSongs;
    }

    /**
     *
     * @param recommendedSongs
     */
    public void setRecommendedSongs(final ArrayList<Song> recommendedSongs) {
        this.recommendedSongs = recommendedSongs;
    }

    /**
     *
     * @return
     */
    public ArrayList<Playlist> getRecommendedPlaylists() {
        return recommendedPlaylists;
    }

    /**
     *
     * @param recommendedPlaylists
     */
    public void setRecommendedPlaylists(final ArrayList<Playlist> recommendedPlaylists) {
        this.recommendedPlaylists = recommendedPlaylists;
    }

    /**
     *
     * @return
     */
    public ArrayList<Merch> getBoughtMerch() {
        return boughtMerch;
    }

    /**
     *
     * @param boughtMerch
     */
    public void setBoughtMerch(final ArrayList<Merch> boughtMerch) {
        this.boughtMerch = boughtMerch;
    }

    /**
     *
     * @return
     */
    public RevenueStats getRevenueStats() {
        return revenueStats;
    }

    /**
     *
     * @param revenueStats
     */
    public void setRevenueStats(final RevenueStats revenueStats) {
        this.revenueStats = revenueStats;
    }
}
