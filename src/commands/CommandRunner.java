package commands;

import json.generator.*;
import library.Library;
import library.Player;
import library.SearchBar;
import library.User;
import library.fields.AlbumFields;
import library.fields.PodcastFields;
import library.fields.StatusFields;
import library.filetypes.AudioFile;
import library.filetypes.Song;
import library.user.helper.RevenueStats;
import library.user.helper.notifications.Notification;
import library.user.helper.wrapped.WrappedStats;
import library.user.helper.wrapped.WrappedStatsUser;
import utils.Errors;

import java.util.ArrayList;
import java.util.HashMap;

public final class CommandRunner {
    private static CommandRunner instance = null;

    private CommandRunner() {

    }

    /**
     * getter for Singleton instance
     * @return instance
     */
    public static CommandRunner getInstance() {
        if (instance == null) {
            instance = new CommandRunner();
        }

        return instance;
    }

    /**
     * method that handles all commands
     * @param inputCommands input from user
     * @param myLibrary the library
     * @return a list of results for generating json
     */
    public ArrayList<Result> runCommand(final InputCommands[] inputCommands,
                                        final Library myLibrary) {
        ArrayList<Result> results = new ArrayList<>();
        int lastCurrentTime = 0;

        for (int i = 0; i < inputCommands.length; i++) {
            int currentTime = inputCommands[i].getTimestamp();
            myLibrary.updatePlayerAllUsers(currentTime - lastCurrentTime,
                    inputCommands[i].getTimestamp());
            lastCurrentTime = currentTime;

            User myUser = myLibrary.getUserByUsername(inputCommands[i].getUsername());

            switch (inputCommands[i].getCommand()) {
                case "search" -> search(myUser, inputCommands[i], myLibrary, results);
                case "select" -> select(myUser, inputCommands[i], myLibrary, results);
                case "load" -> load(myUser, inputCommands[i], myLibrary, results);
                case "playPause" -> playPause(myUser, inputCommands[i], myLibrary, results);
                case "repeat" -> repeat(myUser, inputCommands[i], myLibrary, results);
                case "shuffle" -> shuffle(myUser, inputCommands[i], myLibrary, results);
                case "forward" -> forward(myUser, inputCommands[i], myLibrary, results);
                case "backward" -> backward(myUser, inputCommands[i], myLibrary, results);
                case "like" -> like(myUser, inputCommands[i], myLibrary, results);
                case "next" -> next(myUser, inputCommands[i], myLibrary, results);
                case "prev" -> prev(myUser, inputCommands[i], myLibrary, results);
                case "addRemoveInPlaylist" -> addRemoveInPlaylist(myUser, inputCommands[i],
                        myLibrary, results);
                case "status" -> status(myUser, inputCommands[i], myLibrary, results);
                case "createPlaylist" -> createPlaylist(myUser, inputCommands[i], myLibrary,
                        results);
                case "switchVisibility" -> switchVisibility(myUser, inputCommands[i], myLibrary,
                        results);
                case "follow" -> follow(myUser, inputCommands[i], myLibrary, results);
                case "showPlaylists" -> showPlaylists(myUser, inputCommands[i], myLibrary,
                        results);
                case "showPreferredSongs" -> showPreferredSongs(myUser, inputCommands[i],
                        myLibrary, results);
                case "getTop5Songs" -> getTop5Songs(myUser, inputCommands[i], myLibrary, results);
                case "getTop5Playlists" -> getTop5Playlists(myUser, inputCommands[i], myLibrary,
                        results);
                case "switchConnectionStatus" -> switchConnectionStatus(myUser, inputCommands[i],
                        myLibrary, results);
                case "getOnlineUsers" -> getOnlineUsers(myUser, inputCommands[i], myLibrary,
                        results);
                case "addUser" -> addUser(myUser, inputCommands[i], myLibrary, results);
                case "addAlbum" -> addAlbum(myUser, inputCommands[i], myLibrary, results);
                case "showAlbums" -> showAlbums(myUser, inputCommands[i], myLibrary, results);
                case "printCurrentPage" -> printCurrentPage(myUser, inputCommands[i], myLibrary,
                        results);
                case "addEvent" -> addEvent(myUser, inputCommands[i], myLibrary, results);
                case "addMerch" -> addMerch(myUser, inputCommands[i], myLibrary, results);
                case "getTop5Albums" -> getTop5Albums(myUser, inputCommands[i], myLibrary,
                        results);
                case "getAllUsers" -> getAllUsers(myUser, inputCommands[i], myLibrary, results);
                case "deleteUser" -> deleteUser(myUser, inputCommands[i], myLibrary, results);
                case "addPodcast" -> addPodcast(myUser, inputCommands[i], myLibrary, results);
                case "addAnnouncement" -> addAnnouncement(myUser, inputCommands[i], myLibrary,
                        results);
                case "removeAnnouncement" -> removeAnnouncement(myUser, inputCommands[i],
                        myLibrary, results);
                case "showPodcasts" -> showPodcasts(myUser, inputCommands[i], myLibrary, results);
                case "changePage" -> changePage(myUser, inputCommands[i], myLibrary, results);
                case "removeAlbum" -> removeAlbum(myUser, inputCommands[i], myLibrary, results);
                case "removePodcast" -> removePodcast(myUser, inputCommands[i], myLibrary,
                        results);
                case "removeEvent" -> removeEvent(myUser, inputCommands[i], myLibrary, results);
                case "getTop5Artists" -> getTop5Artists(myUser, inputCommands[i], myLibrary,
                        results);
                case "wrapped" -> wrapped(myUser, inputCommands[i], results);
                case "subscribe" -> subscribe(myUser, inputCommands[i], myLibrary, results);
                case "getNotifications" -> getNotifications(myUser, inputCommands[i], results);
                case "previousPage" -> previousPage(myUser, inputCommands[i], myLibrary, results);
                case "nextPage" -> nextPage(myUser, inputCommands[i], myLibrary, results);
                case "updateRecommendations" -> updateRecommendations(myUser, inputCommands[i],
                        myLibrary, results);
                case "buyMerch" -> buyMerch(myUser, inputCommands[i], myLibrary, results);
                case "loadRecommendations" -> loadRecommendations(myUser, inputCommands[i],
                        myLibrary, results);
                case "seeMerch" -> seeMerch(myUser, inputCommands[i], myLibrary, results);
                default -> System.out.println("Invalid command");
            }
        }

        endProgram(results, myLibrary);
        return results;
    }

    /**
     * method that handles search command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void search(final User myUser, final InputCommands inputCommand,
                       final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<AudioFile> audioFiles = null;
        ArrayList<User> usersFound = null;
        Result searchResult = null;
        if (myUser.isOnline()) {
            if (myUser.getSearchBar() == null) {
                myUser.setSearchBar(new SearchBar(myLibrary, myUser));
            }

            int type = 0;
            if (inputCommand.getType().equals("artist") || inputCommand.getType().equals("host")) {
                usersFound = myUser.getSearchBar().search(inputCommand);
                myUser.getSearchBar().setSearchedUsers(usersFound);
                searchResult = new SearchResult(inputCommand, usersFound);
            } else {
                audioFiles = myUser.getSearchBar().search(inputCommand);
                myUser.getSearchBar().setSearchedAudioFiles(audioFiles);
                searchResult = new SearchResult(inputCommand, audioFiles, type);
            }
        } else {
            searchResult = new SearchResult(inputCommand, null);
        }
        results.add(searchResult);

        myUser.setPlayer(new Player(myUser));
    }

    /**
     * method that handles select command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void select(final User myUser, final InputCommands inputCommand,
                       final Library myLibrary, final ArrayList<Result> results) {
        if (myUser.getSearchBar() == null) {
            myUser.setSearchBar(new SearchBar(myLibrary, myUser));
        }
        int index = myUser.getSearchBar().select(inputCommand.getItemNumber(), inputCommand);
        Result selectResult = new SelectResult(inputCommand, index, myUser);
        results.add(selectResult);
    }

    /**
     * method that handles load command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void load(final User myUser, final InputCommands inputCommand,
                     final Library myLibrary, final ArrayList<Result> results) {
        Player prevPlayer = myUser.getPlayer();
        myUser.setPlayer(new Player(myUser));
        int returnValue = myUser.getPlayer().load(myUser.getSearchBar(), myLibrary);
        if (returnValue < 0) {
            myUser.setPlayer(prevPlayer);
        }
        Result loadResult = new LoadResult(inputCommand, returnValue);
        results.add(loadResult);
    }

    /**
     * method that handles playPause command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void playPause(final User myUser, final InputCommands inputCommand,
                          final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser.getPlayer() == null) {
            returnValue = -1;
        } else {
            returnValue = myUser.getPlayer().playPause();
        }

        Result playPauseResult = new PlayPauseResult(inputCommand, returnValue);
        results.add(playPauseResult);
    }

    /**
     * method that handles repeat command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void repeat(final User myUser, final InputCommands inputCommand,
                       final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.getPlayer().repeat();
        String fileType = myUser.getPlayer().getFileType();
        Result repeatResult = new RepeatResult(inputCommand, returnValue, fileType);
        results.add(repeatResult);
    }

    /**
     * method that handles shuffle command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void shuffle(final User myUser, final InputCommands inputCommand,
                        final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.getPlayer().shuffle(inputCommand.getSeed());
        Result shuffleResult = new ShuffleResult(inputCommand, returnValue);

        results.add(shuffleResult);
    }

    /**
     * method that handles forward command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void forward(final User myUser, final InputCommands inputCommand,
                        final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.getPlayer().forward();

        Result forwardResult = new ForwardBackwardResult(inputCommand, returnValue, 0);
        results.add(forwardResult);
    }

    /**
     * method that handles backward command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void backward(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.getPlayer().backward();

        Result forwardResult = new ForwardBackwardResult(inputCommand, returnValue, 1);
        results.add(forwardResult);
    }

    /**
     * method that handles like command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void like(final User myUser, final InputCommands inputCommand,
                     final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = Errors.USER_NOT_ONLINE;

        if (myUser.isOnline()) {
            returnValue = myUser.getPlayer().like();
        }

        Result likeResult = new LikeResult(inputCommand, returnValue);
        results.add(likeResult);
    }

    /**
     * method that handles next command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void next(final User myUser, final InputCommands inputCommand,
                     final Library myLibrary, final ArrayList<Result> results) {
        String audioFileName = myUser.getPlayer().next();
        Result nextResult = new NextPrevResult(inputCommand, audioFileName, 0);

        results.add(nextResult);
    }

    /**
     * method that handles prev command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void prev(final User myUser, final InputCommands inputCommand,
                     final Library myLibrary, final ArrayList<Result> results) {
        String audioFileName = myUser.getPlayer().prev();

        Result prevResult = new NextPrevResult(inputCommand, audioFileName, 1);
        results.add(prevResult);
    }

    /**
     * method that handles addRemoveInPlaylist command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addRemoveInPlaylist(final User myUser, final InputCommands inputCommand,
                                    final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.getPlayer().addRemoveInPlaylist(inputCommand);
        Result addRemoveInPlaylistResult = new AddRemoveInPlaylistResult(inputCommand,
                returnValue);

        results.add(addRemoveInPlaylistResult);
    }

    /**
     * method that handles status command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void status(final User myUser, final InputCommands inputCommand,
                       final Library myLibrary, final ArrayList<Result> results) {
        StatusFields statusFields = myUser.getPlayer().status();
        Result statusResult = new StatusResult(inputCommand, statusFields);
        results.add(statusResult);
    }

    /**
     * method that handles createPlaylist command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void createPlaylist(final User myUser, final InputCommands inputCommand,
                               final Library myLibrary, final ArrayList<Result> results) {
        if (myUser.getPlayer() == null) {
            myUser.setPlayer(new Player(myUser));
        }
        int returnValue = myUser.createPlaylist(inputCommand, myLibrary);
        Result createPlaylistResult = new CreatePlaylistResult(inputCommand,
                returnValue);
        results.add(createPlaylistResult);
    }

    /**
     * method that handles switchVisibility command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void switchVisibility(final User myUser, final InputCommands inputCommand,
                                 final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.switchVisibility(inputCommand.getPlaylistId());
        Result switchVisibiltyResult = new SwitchVisibilityResult(inputCommand,
                returnValue);
        results.add(switchVisibiltyResult);
    }

    /**
     * method that handles follow command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void follow(final User myUser, final InputCommands inputCommand,
                       final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.followPlaylist();
        Result followPlaylistResult = new FollowPlaylistResult(inputCommand,
                returnValue);
        results.add(followPlaylistResult);
    }

    /**
     * method that handles showPlaylists command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void showPlaylists(final User myUser, final InputCommands inputCommand,
                              final Library myLibrary, final ArrayList<Result> results) {
        Result showPlaylistResult = new ShowPlayResult(inputCommand,
                myUser.getPlaylists());
        results.add(showPlaylistResult);
    }

    /**
     * method that handles showPreferredSongs command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void showPreferredSongs(final User myUser, final InputCommands inputCommand,
                                   final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<Song> preferredSongs = myUser.showPreferredSongs();

        Result preferredSongsResult = new PreferredSongsResult(inputCommand,
                preferredSongs);
        results.add(preferredSongsResult);
    }

    /**
     * method that handles getTop5Songs command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getTop5Songs(final User myUser, final InputCommands inputCommand,
                             final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getTop5(0);

        Result top5Result = new GetTop5Result(inputCommand, result);
        results.add(top5Result);
    }

    /**
     * method that handles getTop5Playlists command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getTop5Playlists(final User myUser, final InputCommands inputCommand,
                                 final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getTop5(1);

        Result top5Result = new GetTop5Result(inputCommand, result);
        results.add(top5Result);
    }

    /**
     * method that handles switchConnectionStatus command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void switchConnectionStatus(final User myUser, final InputCommands inputCommand,
                                       final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.switchConnectivity();
        }

        Result switchConnectivityResult = new SwitchConnectivityResult(inputCommand,
                returnValue);
        results.add(switchConnectivityResult);
    }

    /**
     * method that handles getOnlineUsers command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getOnlineUsers(final User myUser, final InputCommands inputCommand,
                               final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getOnlineUsers();

        Result getOnlineUsersResult = new GetOnlineUsersResult(inputCommand, result);
        results.add(getOnlineUsersResult);
    }

    /**
     * method that handles addUser command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addUser(final User myUser, final InputCommands inputCommand,
                        final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myLibrary.addUser(inputCommand);

        Result addUserResult = new AddUserResult(inputCommand, returnValue);
        results.add(addUserResult);
    }

    /**
     * method that handles addAlbum command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addAlbum(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.addAlbum(inputCommand);
        }

        Result addAlbumResult = new AddAlbumResult(inputCommand, returnValue);
        results.add(addAlbumResult);
    }

    /**
     * method that handles showAlbums command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void showAlbums(final User myUser, final InputCommands inputCommand,
                           final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<AlbumFields> result = myUser.showAlbums();

        Result showAlbumResult = new ShowAlbumResult(inputCommand, result);
        results.add(showAlbumResult);
    }

    /**
     * method that handles printCurrentPage command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void printCurrentPage(final User myUser, final InputCommands inputCommand,
                                 final Library myLibrary, final ArrayList<Result> results) {
        String message = myUser.printCurrentPage();

        Result printCurrentPage = new PrintCurrentPageResult(inputCommand, message);
        results.add(printCurrentPage);
    }

    /**
     * method that handles addEvent command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addEvent(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.addEvent(inputCommand);
        }

        Result addEventResult = new AddEventResult(inputCommand, returnValue);
        results.add(addEventResult);
    }

    /**
     * method that handles addMerch command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addMerch(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.addMerch(inputCommand);
        }

        Result addMerchResult = new AddMerchResult(inputCommand, returnValue);
        results.add(addMerchResult);
    }

    /**
     * method that handles getTop5Albums command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getTop5Albums(final User myUser, final InputCommands inputCommand,
                              final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getTop5(2);

        Result top5Result = new GetTop5Result(inputCommand, result);
        results.add(top5Result);
    }

    /**
     * method that handles getAllUsers command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getAllUsers(final User myUser, final InputCommands inputCommand,
                            final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getAllUsers();

        Result getAllUsersResult = new GetAllUsersResult(inputCommand, result);
        results.add(getAllUsersResult);
    }

    /**
     * method that handles deleteUser command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void deleteUser(final User myUser, final InputCommands inputCommand,
                           final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myLibrary.deleteUser(myUser);
        }

        Result deleteUserResult = new DeleteUserResult(inputCommand, returnValue);
        results.add(deleteUserResult);
    }

    /**
     * method that handles addPodcast command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addPodcast(final User myUser, final InputCommands inputCommand,
                           final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.addPodcast(inputCommand);
        }

        Result addPodcastResult = new AddPodcastResult(inputCommand, returnValue);
        results.add(addPodcastResult);
    }

    /**
     * method that handles addAnnouncement command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void addAnnouncement(final User myUser, final InputCommands inputCommand,
                                final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;

        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.addAnnouncement(inputCommand);
        }

        Result addAnnouncementResult = new AddAnnouncementResult(inputCommand, returnValue);
        results.add(addAnnouncementResult);
    }

    /**
     * method that handles removeAnnouncement command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void removeAnnouncement(final User myUser, final InputCommands inputCommand,
                                   final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.removeAnnouncement(inputCommand);
        }

        Result removeAnnouncementResult = new RemoveAnnouncementResult(inputCommand, returnValue);
        results.add(removeAnnouncementResult);
    }

    /**
     * method that handles showPodcasts command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void showPodcasts(final User myUser, final InputCommands inputCommand,
                             final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<PodcastFields> result = myUser.showPodcasts();

        Result showPodcastResult = new ShowPodcastResult(inputCommand, result);
        results.add(showPodcastResult);
    }

    /**
     * method that handles changePage command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void changePage(final User myUser, final InputCommands inputCommand,
                           final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.changePage(inputCommand, myLibrary);

        Result changePageResult = new NextPageResult(inputCommand, returnValue);
        results.add(changePageResult);
    }

    /**
     * method that handles removeAlbum command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void removeAlbum(final User myUser, final InputCommands inputCommand,
                            final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.removeAlbum(inputCommand);
        }

        Result removeAlbumResult = new RemoveAlbumResult(inputCommand, returnValue);
        results.add(removeAlbumResult);
    }

    /**
     * method that handles removePodcast command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void removePodcast(final User myUser, final InputCommands inputCommand,
                              final Library myLibrary, final ArrayList<Result> results) {

        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.removePodcast(inputCommand);
        }

        Result removePodcastResult = new RemovePodcastResult(inputCommand, returnValue);
        results.add(removePodcastResult);
    }

    /**
     * method that handles removeEvent command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void removeEvent(final User myUser, final InputCommands inputCommand,
                            final Library myLibrary, final ArrayList<Result> results) {

        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.removeEvent(inputCommand);
        }

        Result removeEventResult = new RemoveEventResult(inputCommand, returnValue);
        results.add(removeEventResult);
    }

    /**
     * method that handles getTop5Artists command
     * @param myUser user that gave the command
     * @param inputCommand input from user
     * @param myLibrary library
     * @param results results list for json generation
     */
    public void getTop5Artists(final User myUser, final InputCommands inputCommand,
                               final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = myLibrary.getTop5(3);

        Result top5ArtistsResult = new GetTop5Result(inputCommand, result);
        results.add(top5ArtistsResult);
    }

    public void wrapped(final User myUser, final InputCommands inputCommand,
                        final ArrayList<Result> results) {
        WrappedStats result = myUser.getWrappedStats();
        Result wrappedResult;

        if (!myUser.getStats().isLoaded()) {
            wrappedResult = new WrappedNoDataResult(inputCommand);
        } else {
            wrappedResult = new WrappedResult(inputCommand, result);
        }

        results.add(wrappedResult);
    }

    public void subscribe(final User myUser, final InputCommands inputCommand,
                          final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.subscribe(inputCommand, myLibrary);
        }
        Result subscribeResult = new SubscribeResult(inputCommand, returnValue, myUser.getCurrentPage().getCurrentUserLoaded());
        results.add(subscribeResult);
    }

    public void getNotifications(final User myUser, final InputCommands inputCommand,
                                 final ArrayList<Result> results) {

        ArrayList<Notification> notifications = myUser.getNotifications();

        Result getNotificationsResult = new GetNotificationsResult(inputCommand, notifications);
        results.add(getNotificationsResult);

        myUser.setNotifications(new ArrayList<>());
    }

    public void previousPage(final User myUser, final InputCommands inputCommand,
                             final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.previousPage();

        Result previousNextPageResult = new PreviousNextPageResult(inputCommand, returnValue);
        results.add(previousNextPageResult);
    }

    public void updateRecommendations(final User myUser, final InputCommands inputCommand,
                                      final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.updateRecommendations(inputCommand, myLibrary);
        }

        Result updateRecommendationsResult = new UpdateRecommendationsResult(inputCommand, returnValue);
        results.add(updateRecommendationsResult);
    }

    public void buyMerch(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        int returnValue;
        if (myUser == null) {
            returnValue = Errors.USER_NOT_EXIST;
        } else {
            returnValue = myUser.buyMerch(inputCommand);
        }

        Result buyMerchResult = new BuyMerchResult(inputCommand, returnValue);
        results.add(buyMerchResult);
    }

    public void seeMerch(final User myUser, final InputCommands inputCommand,
                         final Library myLibrary, final ArrayList<Result> results) {
        ArrayList<String> result = null;
        if (myUser != null) {
            result = myUser.seeMerch();
        }

        Result seeMerchResult = new SeeMerchResult(inputCommand, result);
        results.add(seeMerchResult);
    }

    public void loadRecommendations(final User myUser, final InputCommands inputCommand,
                                    final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.loadRecommendations();

        Result loadRecommendationsResult = new LoadRecommendationsResult(inputCommand, returnValue);
        results.add(loadRecommendationsResult);
    }

    public int nextPage(final User myUser, final InputCommands inputCommand,
                        final Library myLibrary, final ArrayList<Result> results) {
        int returnValue = myUser.nextPage();

        Result previousNextPageResult = new PreviousNextPageResult(inputCommand, returnValue);
        results.add(previousNextPageResult);

        return returnValue;
    }

    public void endProgram(final ArrayList<Result> results, final Library myLibrary) {
        HashMap<String, RevenueStats> result = myLibrary.endProgram();
        Result endProgramResult = new EndProgramResult(result);

        results.add(endProgramResult);
    }
}
