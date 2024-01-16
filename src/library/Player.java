package library;


import commands.InputCommands;
import library.fields.StatusFields;
import library.filetypes.Album;
import library.filetypes.AudioFile;
import library.filetypes.Playlist;
import library.filetypes.Podcast;
import library.filetypes.Song;
import library.user.helper.wrapped.WrappedStatsArtist;
import library.user.helper.wrapped.WrappedStatsHost;
import library.user.helper.wrapped.WrappedStatsUser;
import utils.Errors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Player {
    private AudioFile audioFile;
    private int repeatState;
    private String fileType; //song / podcast / playlist / album
    private boolean paused;
    private boolean shuffle;
    private int currentTime;
    private User user;
    private int currentId;
    private List<Integer> shuffledIndexes;
    private int shuffleId;
    private static final int FORWARD_TIME = 90;
    private int shuffleSeed;
    private static final int REPEAT_STATE_3 = 3;

    /**
     * constructor
     */
    public Player() {
        this.repeatState = 0;
        this.paused = true;
        this.shuffle = false;
        this.audioFile = Library.NULL_AUDIO_FILE;
    }

    /**
     * constructor
     * @param user the current user
     */
    public Player(final User user) {
        this.repeatState = 0;
        this.paused = true;
        this.shuffle = false;
        this.user = user;
        this.audioFile = Library.NULL_AUDIO_FILE;
    }

    /**
     * method that calculates the revenue of songs listened to before the ad break
     * @param library the library
     * @param adPrice the price of the ad
     * @return a number referring to an error, similar to other methods
     */
    public int adBreak(final Library library, final int adPrice) {
        if (audioFile.equals(Library.NULL_AUDIO_FILE)) {
            return Errors.USER_NOT_PLAYING;
        }

        double revenuePerSong = (double) adPrice / user.getNonPremiumSongList().size();

        System.out.println((double) adPrice + " " + user.getNonPremiumSongList().size() + " "
                + revenuePerSong);

        for (Song song : user.getNonPremiumSongList()) {
            song.setRevenue(song.getRevenue() + revenuePerSong);

            double currentArtistRevenue = library.getUserByUsername(song.getOwner()).
                    getRevenueStats().getSongRevenue();
            library.getUserByUsername(song.getOwner()).getRevenueStats().
                    setSongRevenue(currentArtistRevenue + revenuePerSong);
        }

        user.setNonPremiumSongList(new ArrayList<>());

        return 0;
    }

    /**
     * method that loads the last recommendation for the current user
     */
    public void loadRecommendations() {
        currentId = 0;
        currentTime = 0;

        if (user.getLastRecommandationType().equals("random_song")) {
            audioFile = user.getRecommendedSongs().get(user.getRecommendedSongs().size() - 1);

            fileType = "song";
        } else {
            audioFile = user.getRecommendedPlaylists().get(user.getRecommendedPlaylists().
                    size() - 1);

            fileType = "playlist";
        }
    }

    /**
     * method that handles the load command
     *
     * @param searchBar of the current user
     * @return a number referring to an error, similar to other methods
     */
    public int load(final SearchBar searchBar, final Library library) {
        if (searchBar.getSelectedAudioFile() == null) {
            return -1;
        }

        audioFile = searchBar.getSelectedAudioFile();
        fileType = searchBar.getType();

        user.getStats().setLoaded(true);
        if (library.getUserByUsername(audioFile.getOwner()) != null) {
            library.getUserByUsername(audioFile.getOwner()).getStats().setLoaded(true);
        }

        if (fileType.equals("playlist")) {
            currentId = 0;
        } else if (fileType.equals("podcast")) {
            if (user.getPodcastInfoMap().containsKey(audioFile.getName())) {
                currentId = user.getPodcastInfoMap().get(audioFile.getName()).getCurrentId();
                currentTime = user.getPodcastInfoMap().get(audioFile.getName()).getCurrentTime();
            } else {
                currentId = 0;
                currentTime = 0;
            }

            int listenedTimes = 0;
            Podcast podcast = (Podcast) audioFile;

            WrappedStatsUser userStats = (WrappedStatsUser) user.getWrappedStatsUser();
            listenedTimes = userStats.getTopEpisodes().getOrDefault(podcast.getEpisodes().
                    get(currentId).getName(), 0);
            userStats.getTopEpisodes().put(podcast.getEpisodes().
                    get(currentId).getName(), listenedTimes + 1);

            User host = library.getUserByUsername(podcast.getOwner());
            System.out.println(podcast.getOwner());

            if (host != null) {
                WrappedStatsHost hostStats = (WrappedStatsHost) host.getStats();
                listenedTimes = hostStats.getTopEpisodes().getOrDefault(podcast.getEpisodes().
                        get(currentId).getName(), 0);
                hostStats.getTopEpisodes().put(podcast.getEpisodes().
                        get(currentId).getName(), listenedTimes + 1);

                listenedTimes = hostStats.getListenersHash().getOrDefault(user.getUsername(), 0);
                hostStats.getListenersHash().put(user.getUsername(), listenedTimes + 1);
            }

        } else if (fileType.equals("album")) {
            currentId = 0;
            int listenedTimes;
            User artist = library.getUserByUsername(audioFile.getOwner());
            artist.getRevenueStats().setArtistLoaded(true);

            Album album = (Album) audioFile;
            WrappedStatsUser userStats = (WrappedStatsUser) user.getWrappedStatsUser();

            listenedTimes = userStats.getTopAlbums().getOrDefault(album.getName(), 0);
            userStats.getTopAlbums().put(album.getName(), listenedTimes + 1);

            listenedTimes = userStats.getTopArtists().getOrDefault(album.getOwner(), 0);
            userStats.getTopArtists().put(album.getOwner(), listenedTimes + 1);

            listenedTimes = userStats.getTopSongs().getOrDefault(album.getSongs().
                    get(0).getName(), 0);
            userStats.getTopSongs().put(album.getSongs().get(0).getName(), listenedTimes + 1);

            listenedTimes = userStats.getTopGenres().getOrDefault(album.getSongs().
                    get(0).getGenre(), 0);
            userStats.getTopGenres().put(album.getSongs().get(0).getGenre(), listenedTimes + 1);

            artist = library.getUserByUsername(album.getOwner());
            WrappedStatsArtist artistStats = (WrappedStatsArtist) artist.getStats();

            listenedTimes = artistStats.getTopSongs().getOrDefault(album.getSongs().
                    get(0).getName(), 0);
            artistStats.getTopSongs().put(album.getSongs().get(0).getName(), listenedTimes + 1);

            listenedTimes = artistStats.getTopAlbums().getOrDefault(album.getName(), 0);
            artistStats.getTopAlbums().put(album.getName(), listenedTimes + 1);

            listenedTimes = artistStats.getListenersHash().getOrDefault(user.getUsername(), 0);
            artistStats.getListenersHash().put(user.getUsername(), listenedTimes + 1);

            artist.getTotalSongsListened().add(album.getSongs().get(currentId));

            if (user.isPremium()) {
                user.getPremiumSongList().add(album.getSongs().get(currentId));
            } else {
                user.getNonPremiumSongList().add(album.getSongs().get(currentId));
            }
        } else if (fileType.equals("song")) {
            currentId = 0;

            int listenedTimes = 0;
            Song song = (Song) audioFile;
            User artist = library.getUserByUsername(audioFile.getOwner());
            artist.getRevenueStats().setArtistLoaded(true);

            WrappedStatsUser userStats = (WrappedStatsUser) user.getWrappedStatsUser();

            listenedTimes = userStats.getTopSongs().getOrDefault(song.getName(), 0);
            userStats.getTopSongs().put(song.getName(), listenedTimes + 1);

            listenedTimes = userStats.getTopAlbums().getOrDefault(song.getAlbum(), 0);
            userStats.getTopAlbums().put(song.getAlbum(), listenedTimes + 1);

            listenedTimes = userStats.getTopArtists().getOrDefault(song.getOwner(), 0);
            userStats.getTopArtists().put(song.getOwner(), listenedTimes + 1);

            listenedTimes = userStats.getTopGenres().getOrDefault(song.getGenre(), 0);
            userStats.getTopGenres().put(song.getGenre(), listenedTimes + 1);

            artist = library.getUserByUsername(song.getOwner());
            WrappedStatsArtist artistStats = (WrappedStatsArtist) artist.getStats();

            listenedTimes = artistStats.getTopSongs().getOrDefault(song.getName(), 0);
            artistStats.getTopSongs().put(song.getName(), listenedTimes + 1);

            listenedTimes = artistStats.getTopAlbums().getOrDefault(song.getAlbum(), 0);
            artistStats.getTopAlbums().put(song.getAlbum(), listenedTimes + 1);

            listenedTimes = artistStats.getListenersHash().getOrDefault(user.getUsername(), 0);
            artistStats.getListenersHash().put(user.getUsername(), listenedTimes + 1);

            artist.getTotalSongsListened().add(song);

            if (user.isPremium()) {
                user.getPremiumSongList().add(song);
            } else {
                user.getNonPremiumSongList().add(song);
            }
        }
        paused = false;
        searchBar.setSearchedAudioFiles(null);
        searchBar.setSelectedAudioFile(null);
        return 0;
    }

    /**
     * the method that handles play and pause commands
     *
     * @return an int referring to a different error
     * negative - not successful
     * positive - successful, 1 or 0 depending on whether it's a play or pause command
     */
    public int playPause() {
        int currentState;

        if (audioFile == Library.NULL_AUDIO_FILE) {
            return -1;
        }

        if (paused) {
            currentState = 0;
        } else {
            currentState = 1;
        }

        paused = !paused;

        return currentState;
    }

    /**
     * method that handles the 'status' command
     *
     * @return a 'StatusField' instance which is filled with the details required by this
     * command (filename, time remaining of the current song / episode, the state of repeat etc.)
     */
    public StatusFields status() {
        String repeatString;
        if (repeatState == 0) {
            repeatString = "No Repeat";
        } else if (repeatState == 1) {
            if (fileType.equals("song") || fileType.equals("podcast")) {
                repeatString = "Repeat Once";
            } else {
                repeatString = "Repeat All";
            }
        } else {
            if (fileType.equals("song") || fileType.equals("podcast")) {
                repeatString = "Repeat Infinite";
            } else {
                repeatString = "Repeat Current Song";
            }
        }
        int time = 0;
        String currentFileName = getAudioFile().getName();
        if (audioFile != Library.NULL_AUDIO_FILE) {
            if (fileType.equals("song")) {
                Song song = (Song) audioFile;
                time = song.getDuration() - currentTime;
            } else if (fileType.equals("playlist")) {
                Playlist playlist = (Playlist) audioFile;
                time = playlist.getSongList().get(currentId).getDuration() - currentTime;
                currentFileName = playlist.getSongList().get(currentId).getName();
            } else if (fileType.equals("podcast")) {
                Podcast podcast = (Podcast) audioFile;
                time = podcast.getEpisodes().get(currentId).getDuration() - currentTime;
                currentFileName = podcast.getEpisodes().get(currentId).getName();
            } else if (fileType.equals("album")) {
                Album album = (Album) audioFile;
                time = album.getSongs().get(currentId).getDuration() - currentTime;
                currentFileName = album.getSongs().get(currentId).getName();
            }
        }
        return new StatusFields(currentFileName, time, repeatString, shuffle, paused);
    }

    /**
     * method that handles both commands of adding and removing the current loaded song in a
     * playlist
     *
     * @param inputCommand in order to get the index of the playlist i want to insert the current
     *                     song into
     * @return an int determining the type of error the command received, negative numbers suggest
     * various errors, while positive ones represent that the command has been executed
     * successfully
     */
    public int addRemoveInPlaylist(final InputCommands inputCommand) {
        int index = inputCommand.getPlaylistId();

        if (index > user.getPlaylists().size()) {
            return Errors.USER_NOT_EXIST;
        }

        if (audioFile == null || audioFile == Library.NULL_AUDIO_FILE) {
            return Errors.NOT_LOADED;
        }

        if (!fileType.equals("song") && !fileType.equals("album")) {
            return Errors.NOT_SONG;
        }

        Playlist currPlaylist = user.getPlaylists().get(index - 1);
        if (fileType.equals("song")) {
            for (int i = 0; i < currPlaylist.getSongList().size(); i++) {
                if (currPlaylist.getSongList().get(i) == audioFile) {
                    currPlaylist.getSongList().remove(i);
                    return 0;
                }
            }

            currPlaylist.getSongList().add((Song) audioFile);
        } else {
            Album album = (Album) audioFile;
            Song currentSong = album.getSongs().get(currentId);

            for (int i = 0; i < currPlaylist.getSongList().size(); i++) {
                if (currPlaylist.getSongList().get(i) == currentSong) {
                    currPlaylist.getSongList().remove(i);
                    return 0;
                }
            }

            currPlaylist.getSongList().add(currentSong);
        }
        return 1;
    }

    /**
     * method that handles the like command
     *
     * @return an int that aids in
     */
    public int like() {
        if (audioFile == null || audioFile == Library.NULL_AUDIO_FILE) {
            return Errors.NOT_LOADED;
        }

        if (!fileType.equals("song") && !fileType.equals("playlist") && !fileType.equals("album")) {
            return Errors.NOT_SONG;
        }

        Song currentSong;
        if (fileType.equals("song")) {
            currentSong = (Song) audioFile;
        } else if (fileType.equals("playlist")) {
            currentSong = ((Playlist) audioFile).getSongList().get(currentId);
        } else {
            currentSong = ((Album) audioFile).getSongs().get(currentId);
        }

        for (int i = 0; i < user.getLikedSongs().size(); i++) {
            if (user.getLikedSongs().get(i).equals(currentSong)) {
                user.getLikedSongs().remove(i);
                currentSong.setNrLikes(currentSong.getNrLikes() - 1);
                return 0;
            }
        }
        user.getLikedSongs().add(currentSong);
        currentSong.setNrLikes(currentSong.getNrLikes() + 1);

        return 1;
    }

    /**
     * method that handles the repeat command
     *
     * @return an int to handle the error in Result class
     */
    public int repeat() {
        if (audioFile == Library.NULL_AUDIO_FILE) {
            return -1;
        }

        repeatState++;
        if (repeatState == REPEAT_STATE_3) {
            repeatState = 0;
        }
        return repeatState;
    }

    /**
     * generates a list with each element having the value of his index (v[i] = i)
     *
     * @param range - the number of elements the generated list will have
     * @return the newly generated list
     */
    static List<Integer> generateSequence(final int range) {
        return IntStream.rangeClosed(0, range - 1).boxed().collect(Collectors.toList());
    }

    /**
     * method that handles shuffle command
     *
     * @param seed that determines the randomness of the shuffle
     * @return a number referring to an error, used to determine whether the command
     * has successfully been executed or not
     */
    public int shuffle(final int seed) {
        shuffle = !shuffle;

        if (audioFile == Library.NULL_AUDIO_FILE) {
            shuffle = false;
            return Errors.NOT_LOADED;
        }

        if (!fileType.equals("playlist") && !fileType.equals("album")) {
            return Errors.NOT_PLAYLIST;
        }

        int size;
        if (fileType.equals("playlist")) {
            size = ((Playlist) audioFile).getSongList().size();
        } else {
            size = ((Album) audioFile).getSongs().size();
        }

        if (shuffle) {
            this.shuffleSeed = seed;
            shuffleId = currentId;
            shuffledIndexes = generateSequence(size);

            Collections.shuffle(shuffledIndexes, new Random(seed));

            shuffleId = shuffledIndexes.indexOf(currentId);

            return 0;
        } else {
            currentId = shuffledIndexes.get(shuffleId);
        }

        return 1;
    }

    /**
     * method that handles the next command
     *
     * @return the name of the song / episode after the operation happens
     */
    public String next() {
        if (audioFile == Library.NULL_AUDIO_FILE) {
            return null;
        }

        currentTime = 0;
        paused = false;

        if (fileType.equals("playlist")) {
            Playlist playlist = (Playlist) audioFile;

            if (repeatState == 2) {
                return playlist.getSongList().get(currentId).getName();
            }

            if (shuffle) {
                shuffleId++;

                if (shuffleId < shuffledIndexes.size()) {
                    int newId = shuffledIndexes.get(shuffleId);
                    currentId = newId;
                } else {
                    currentId = shuffledIndexes.size();
                }
            } else {
                currentId++;
            }


            if (playlist.getSongList().size() == currentId) {
                if (repeatState > 0) {
                    currentId = 0;
                } else {
                    audioFile = Library.NULL_AUDIO_FILE;
                    currentTime = 0;
                    currentId = 0;
                    paused = true;
                    shuffle = false;
                    return null;
                }
            }

            return playlist.getSongList().get(currentId).getName();
        }

        if (fileType.equals("podcast")) {
            currentId++;
            Podcast podcast = (Podcast) audioFile;

            if (repeatState == 1) {
                currentId--;
            }

            if (podcast.getEpisodes().size() == currentId) {
                if (repeatState > 0) {
                    currentId = 0;
                } else {
                    return null;
                }
            }

            return podcast.getEpisodes().get(currentId).getName();
        }

        if (fileType.equals("album")) {
            Album album = (Album) audioFile;

            if (repeatState == 2) {
                return album.getSongs().get(currentId).getName();
            }

            if (shuffle) {
                shuffleId++;

                if (shuffleId < shuffledIndexes.size()) {
                    int newId = shuffledIndexes.get(shuffleId);
                    currentId = newId;
                } else {
                    currentId = shuffledIndexes.size();
                }
            } else {
                currentId++;
            }

            if (album.getSongs().size() == currentId) {
                if (repeatState > 0) {
                    currentId = 0;
                } else {
                    audioFile = Library.NULL_AUDIO_FILE;
                    currentTime = 0;
                    currentId = 0;
                    paused = true;
                    shuffle = false;
                    return null;
                }
            }

            return album.getSongs().get(currentId).getName();
        }

        return null;
    }

    /**
     * method that handles the previous command
     *
     * @return the name of the current song / episode after the operation happens
     */
    public String prev() {
        if (audioFile == Library.NULL_AUDIO_FILE) {
            return null;
        }

        paused = false;

        if (currentTime > 0) {
            currentTime = 0;

        } else {
            currentTime = 0;

            if (shuffle) {
                shuffleId--;

                if (shuffleId >= 0) {
                    int newId = shuffledIndexes.get(shuffleId);
                    currentId = newId;
                } else {
                    shuffleId = 0;
                    currentId = shuffledIndexes.get(0);
                }
            } else {
                currentId--;
                if (currentId < 0) {
                    currentId = 0;
                }
            }

        }

        if (fileType.equals("playlist")) {
            Playlist playlist = (Playlist) audioFile;

            return playlist.getSongList().get(currentId).getName();
        } else if (fileType.equals("podcast")) {
            Podcast podcast = (Podcast) audioFile;

            return podcast.getEpisodes().get(currentId).getName();
        } else if (fileType.equals("album")) {
            Album album = (Album) audioFile;

            return album.getSongs().get(currentId).getName();
        }

        return null;
    }

    /**
     * the method that handles the forward command
     *
     * @return a number referring to an error, similar to other command
     */
    public int forward() {
        if (audioFile == Library.NULL_AUDIO_FILE) {
            return Errors.NOT_LOADED;
        }

        if (!fileType.equals("podcast")) {
            return Errors.NOT_PODCAST;
        }

        currentTime += FORWARD_TIME;

        Podcast podcast = (Podcast) audioFile;
        if (currentTime > podcast.getEpisodes().get(currentId).getDuration()) {
            currentId++;
            currentTime = 0;
        }
        return 0;
    }

    /**
     * the method that handles the backward command
     *
     * @return a number referring to an error, similar to other commands
     */
    public int backward() {
        if (audioFile == Library.NULL_AUDIO_FILE) {
            return Errors.NOT_LOADED;
        }

        if (!fileType.equals("podcast")) {
            return Errors.NOT_PODCAST;
        }

        currentTime -= FORWARD_TIME;

        if (currentTime < 0) {
            currentTime = 0;
        }

        return 0;
    }

    /**
     *
     * @return
     */
    public AudioFile getAudioFile() {
        return audioFile;
    }

    /**
     *
     * @param audioFile
     */
    public void setAudioFile(final AudioFile audioFile) {
        this.audioFile = audioFile;
    }

    /**
     *
     * @return
     */
    public int getRepeatState() {
        return repeatState;
    }

    /**
     *
     * @param repeatState
     */
    public void setRepeatState(final int repeatState) {
        this.repeatState = repeatState;
    }

    /**
     *
     * @return
     */
    public String getFileType() {
        return fileType;
    }

    /**
     *
     * @param fileType
     */
    public void setFileType(final String fileType) {
        this.fileType = fileType;
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

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(final User user) {
        this.user = user;
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
    public List<Integer> getShuffledIndexes() {
        return shuffledIndexes;
    }

    /**
     *
     * @param shuffledIndexes
     */
    public void setShuffledIndexes(final List<Integer> shuffledIndexes) {
        this.shuffledIndexes = shuffledIndexes;
    }

    /**
     *
     * @return
     */
    public int getShuffleId() {
        return shuffleId;
    }

    /**
     *
     * @param shuffleId
     */
    public void setShuffleId(final int shuffleId) {
        this.shuffleId = shuffleId;
    }

    /**
     *
     * @return
     */
    public int getSeed() {
        return shuffleSeed;
    }

    /**
     *
     * @param seed
     */
    public void setSeed(final int seed) {
        this.shuffleSeed = seed;
    }
}
