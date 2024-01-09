package library;

import commands.InputCommands;
import commands.filters.SongFilter;
import commands.filters.PodcastFilter;
import commands.filters.PlaylistFilter;
import commands.filters.ReleaseYearFilter;
import commands.filters.AlbumFilter;
import commands.filters.Filter;
import library.filetypes.Album;
import library.filetypes.AudioFile;
import library.filetypes.Playlist;
import library.filetypes.Podcast;
import library.filetypes.Song;

import java.util.ArrayList;

public class SearchBar {
    private String type;
    private ArrayList<AudioFile> searchedAudioFiles;
    private ArrayList<User> searchedUsers;
    private AudioFile selectedAudioFile;
    private User selectedUser;
    private Filter filter;
    private final Library library;
    private User user;
    private static final int COUNTMAX = 5;

    /**
     * Constructs a SearchBar object with the specified library and user.
     *
     * @param library The library to be associated with the search bar.
     * @param user    The user interacting with the search bar.
     */
    public SearchBar(final Library library, final User user) {
        this.library = library;
        this.user = user;
    }

    /**
     * method that sets the field filter based of type of file is in the input command
     *
     * @param inputCommand of the user
     */
    public void setFilter(final InputCommands inputCommand) {
        if (inputCommand.getType().equals("song")) {
            this.filter = new SongFilter(inputCommand.getFilters());
        } else if (inputCommand.getType().equals("podcast")) {
            this.filter = new PodcastFilter(inputCommand.getFilters());
        } else if (inputCommand.getType().equals("playlist")) {
            this.filter = new PlaylistFilter(inputCommand.getFilters());
        } else if (inputCommand.getType().equals("artist")) {
            this.filter = new Filter(inputCommand.getFilters());
        } else if (inputCommand.getType().equals("host")) {
            this.filter = new Filter(inputCommand.getFilters());
        } else if (inputCommand.getType().equals("album")) {
            this.filter = new AlbumFilter(inputCommand.getFilters());
        }
    }

    /**
     * search operation specifically for songs
     *
     * @return a list of the found songs based on the filters given
     */
    public ArrayList<Song> searchSong() {
        searchedUsers = null;

        int count = 0;
        ArrayList<Song> songList = new ArrayList<>();
        SongFilter songFilters = (SongFilter) filter;
        for (int i = 0; i < library.getSongs().size() && count < COUNTMAX; i++) {
            if (songFilters.getName() != null && !library.getSongs().get(i).getName().
                    startsWith(songFilters.getName())) {
                continue;
            }

            if (songFilters.getAlbum() != null && !library.getSongs().get(i).getAlbum().
                    equals(songFilters.getAlbum())) {
                continue;
            }

            if (songFilters.getGenre() != null && !library.getSongs().get(i).getGenre().
                    toLowerCase().equals(songFilters.getGenre().toLowerCase())) {
                continue;
            }

            if (songFilters.getArtist() != null && !library.getSongs().get(i).getOwner().
                    equals(songFilters.getArtist())) {
                continue;
            }

            if (songFilters.getLyrics() != null && !library.getSongs().get(i).getLyrics().
                    toLowerCase().contains(songFilters.getLyrics().toLowerCase())) {
                continue;
            }

            if (songFilters.getTags() != null) {
                boolean containsAllTags = true;

                for (int j = 0; j < songFilters.getTags().size() && containsAllTags; j++) {
                    if (!library.getSongs().get(i).getTags().contains(songFilters.getTags().
                            get(j))) {
                        containsAllTags = false;
                    }
                }

                if (!containsAllTags) {
                    continue;
                }
            }

            if (songFilters.getReleaseYear() != null) {
                ReleaseYearFilter releaseYearFilter = new ReleaseYearFilter(songFilters.
                        getReleaseYear());
                boolean included = false;

                if (releaseYearFilter.greaterThan(library.getSongs().get(i).getReleaseYear())) {
                    included = true;
                }

                if (releaseYearFilter.lowerThan(library.getSongs().get(i).getReleaseYear())) {
                    included = true;
                }

                if (!included) {
                    continue;
                }
            }

            songList.add(library.getSongs().get(i));
            count++;
        }

        return songList;
    }

    /**
     * search function for podcasts only
     *
     * @return a list of the podcasts found based on the filters given in the input
     */
    public ArrayList<Podcast> searchPodcast() {
        searchedUsers = null;

        int count = 0;
        ArrayList<Podcast> podcastList = new ArrayList<>();
        PodcastFilter podcastFilter = (PodcastFilter) filter;

        for (int i = 0; i < library.getPodcasts().size() && count < COUNTMAX; i++) {
            if (podcastFilter.getName() != null && !library.getPodcasts().get(i).getName().
                    startsWith(podcastFilter.getName())) {
                continue;
            }

            if (podcastFilter.getOwner() != null && !library.getPodcasts().get(i).getOwner().
                    equals(podcastFilter.getOwner())) {
                continue;
            }

            count++;
            podcastList.add(library.getPodcasts().get(i));

        }

        return podcastList;
    }

    /**
     * the search method used specifically for playlists
     *
     * @return a list of playlists found based off the filter given by the input user
     */
    public ArrayList<Playlist> searchPlaylist() {
        searchedUsers = null;

        int count = 0;
        ArrayList<Playlist> playlistList = new ArrayList<>();
        PlaylistFilter playlistFilter = (PlaylistFilter) filter;
        for (int i = 0; i < user.getPlaylists().size() && count < COUNTMAX; i++) {
            if (playlistFilter.getName() != null && !user.getPlaylists().get(i).getName().
                    startsWith(playlistFilter.getName())) {
                continue;
            }

            if (playlistFilter.getOwner() != null && !user.getPlaylists().get(i).getOwner().
                    equals(playlistFilter.getOwner())) {
                continue;
            }

            count++;
            playlistList.add(user.getPlaylists().get(i));
        }

        for (int i = 0; i < library.getUsers().size() && count < COUNTMAX; i++) {
            User currentUser = library.getUsers().get(i);
            if (currentUser.getUsername().equals(user.getUsername())
                    || !currentUser.getUserType().equals("user")) {
                continue;
            }

            for (int j = 0; j < currentUser.getPlaylists().size(); j++) {
                Playlist currentPlaylist = currentUser.getPlaylists().get(j);

                if (!currentPlaylist.isVisible()) {
                    continue;
                }

                if (playlistFilter.getName() != null && !currentPlaylist.getName().
                        startsWith(playlistFilter.getName())) {
                    continue;
                }

                if (playlistFilter.getOwner() != null && !currentPlaylist.getOwner().
                        equals(playlistFilter.getOwner())) {
                    continue;
                }


                count++;
                playlistList.add(currentPlaylist);
            }
        }

        selectedAudioFile = null;
        return playlistList;
    }

    /**
     * the search method used specifically for artists
     *
     * @return a list of artists found based off the filter given by the input user
     */

    public ArrayList<User> searchArtist() {
        searchedAudioFiles = null;

        int count = 0;
        ArrayList<User> artistsList = new ArrayList<>();
        Filter artistFilter = (Filter) filter;

        for (int i = 0; i < library.getUsers().size() && count < COUNTMAX; i++) {
            if (!library.getUsers().get(i).getUserType().equals("artist")) {
                continue;
            }

            if (artistFilter.getName() != null && !library.getUsers().get(i).getUsername().
                    startsWith(artistFilter.getName())) {
                continue;
            }

            count++;
            artistsList.add(library.getUsers().get(i));

        }

        return artistsList;
    }

    /**
     * the search method used specifically for hosts
     *
     * @return a list of hosts found based off the filter given by the input user
     */

    public ArrayList<User> searchHost() {
        searchedAudioFiles = null;

        int count = 0;
        ArrayList<User> hostsList = new ArrayList<>();
        Filter hostsFilter = (Filter) filter;

        for (int i = 0; i < library.getUsers().size() && count < COUNTMAX; i++) {
            if (!library.getUsers().get(i).getUserType().equals("host")) {
                continue;
            }

            if (hostsFilter.getName() != null && !library.getUsers().get(i).getUsername().
                    startsWith(hostsFilter.getName())) {
                continue;
            }

            count++;
            hostsList.add(library.getUsers().get(i));

        }

        return hostsList;
    }

    /**
     * the search method used specifically for albums
     *
     * @return a list of albums found based off the filter given by the input user
     */

    public ArrayList<Album> searchAlbum() {
        searchedUsers = null;

        int count = 0;
        ArrayList<Album> albumList = new ArrayList<>();
        AlbumFilter albumFilter = (AlbumFilter) filter;

        for (int i = 0; i < Library.albumList.size() && count < COUNTMAX; i++) {
            if (albumFilter.getName() != null && !Library.albumList.get(i).getName().
                    startsWith(albumFilter.getName())) {
                continue;
            }

            if (albumFilter.getOwner() != null && !Library.albumList.get(i).getOwner().
                    equals(albumFilter.getOwner())) {
                continue;
            }

            if (albumFilter.getDescription() != null && !Library.albumList.get(i).getDescription().
                    equalsIgnoreCase(albumFilter.getDescription())) {
                continue;
            }


            count++;
            albumList.add(Library.albumList.get(i));

        }

        return albumList;
    }

    /**
     * the method that handles the search command which calls a different method based on the
     * type of file / users we are trying to search
     *
     * @param inputCommand - used to get the type of audio file
     * @return a list of the found audio files or users
     */
    public ArrayList search(final InputCommands inputCommand) {
        this.type = inputCommand.getType();
        setFilter(inputCommand);
        if (type.equals("song")) {
            return searchSong();
        } else if (type.equals("podcast")) {
            return searchPodcast();
        } else if (type.equals("playlist")) {
            return searchPlaylist();
        } else if (type.equals("artist")) {
            return searchArtist();
        } else if (type.equals("album")) {
            return searchAlbum();
        } else if (type.equals("host")) {
            return searchHost();
        }

        return searchHost();
    }

    /**
     * method that handles the select command
     *
     * @param itemNumber is the id in the list of searched audio files we want to select
     * @return a number referring to an error indicating whether the selection has been done
     * or not
     */
    public int select(final int itemNumber, final InputCommands inputCommand) {
        if (searchedAudioFiles == null && searchedUsers == null) {
            return -2;
        }

        if (searchedAudioFiles != null) {
            if (itemNumber > searchedAudioFiles.size()) {
                return -1;
            }

            selectedAudioFile = searchedAudioFiles.get(itemNumber - 1);

        } else {
            if (itemNumber > searchedUsers.size()) {
                return -1;
            }

            selectedUser = searchedUsers.get(itemNumber - 1);

            if (selectedUser.getUserType().equals("artist")) {
                user.getCurrentPage().setCurrentPageType("Artist");
            } else if (selectedUser.getUserType().equals("host")) {
                user.getCurrentPage().setCurrentPageType("Host");
            }
            user.getCurrentPage().setCurrentUserLoaded(selectedUser);
            user.getSearchBar().setSearchedUsers(null);

            return -3;
        }

        return itemNumber - 1;
    }

    /**
     *
     * @return
     */
    public ArrayList<AudioFile> getSearchedAudioFiles() {
        return searchedAudioFiles;
    }

    /**
     *
     * @param searchedAudioFiles
     */
    public void setSearchedAudioFiles(final ArrayList<AudioFile> searchedAudioFiles) {
        this.searchedAudioFiles = searchedAudioFiles;
    }

    /**
     *
     * @return
     */
    public AudioFile getSelectedAudioFile() {
        return selectedAudioFile;
    }

    /**
     *
     * @param selectedAudioFile
     */
    public void setSelectedAudioFile(final AudioFile selectedAudioFile) {
        this.selectedAudioFile = selectedAudioFile;
    }

    /**
     *
     * @return
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     */
    public void setFilter(final Filter filter) {
        this.filter = filter;
    }

    /**
     *
     * @return
     */
    public Library getLibrary() {
        return library;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public ArrayList<User> getSearchedUsers() {
        return searchedUsers;
    }

    /**
     *
     * @param searchedUsers
     */
    public void setSearchedUsers(final ArrayList<User> searchedUsers) {
        this.searchedUsers = searchedUsers;
    }

    /**
     *
     * @return
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     *
     * @param selectedUser
     */
    public void setSelectedUser(final User selectedUser) {
        this.selectedUser = selectedUser;
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
}
