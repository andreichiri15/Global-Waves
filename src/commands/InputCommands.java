package commands;

import commands.filters.AllFilters;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;

import java.util.ArrayList;

public class InputCommands {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private AllFilters filter;
    private int itemNumber;
    private int seed;
    private int playlistId;
    private String playlistName;
    private int age;
    private String city;
    private int releaseYear;
    private String description;
    private String name;
    private ArrayList<SongInput> songs;
    private String date;
    private int price;
    private ArrayList<EpisodeInput> episodes;
    private String nextPage;

    /**
     *
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command
     */
    public void setCommand(final String command) {
        this.command = command;
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
    public int getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
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
    public AllFilters getFilters() {
        return filter;
    }

    /**
     *
     * @param filters
     */
    public void setFilters(final AllFilters filters) {
        this.filter = filters;
    }

    /**
     *
     * @return
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     *
     * @param itemNumber
     */
    public void setItemNumber(final int itemNumber) {
        this.itemNumber = itemNumber;
    }

    /**
     *
     * @return
     */
    public int getSeed() {
        return seed;
    }

    /**
     *
     * @param seed
     */
    public void setSeed(final int seed) {
        this.seed = seed;
    }

    /**
     *
     * @return
     */
    public int getPlaylistId() {
        return playlistId;
    }

    /**
     *
     * @param playlistId
     */
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }

    /**
     *
     * @return
     */
    public String getPlaylistName() {
        return playlistName;
    }

    /**
     *
     * @param playlistName
     */
    public void setPlaylistName(final String playlistName) {
        this.playlistName = playlistName;
    }

    /**
     *
     * @return
     */
    public AllFilters getFilter() {
        return filter;
    }

    /**
     *
     * @param filter
     */
    public void setFilter(final AllFilters filter) {
        this.filter = filter;
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
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     *
     * @param releaseYear
     */
    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
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
    public ArrayList<SongInput> getSongs() {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(final ArrayList<SongInput> songs) {
        this.songs = songs;
    }

    /**
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public ArrayList<EpisodeInput> getEpisodes() {
        return episodes;
    }

    /**
     *
     * @param episodes
     */
    public void setEpisodes(final ArrayList<EpisodeInput> episodes) {
        this.episodes = episodes;
    }

    /**
     *
     * @return
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     *
     * @param nextPage
     */
    public void setNextPage(final String nextPage) {
        this.nextPage = nextPage;
    }
}
