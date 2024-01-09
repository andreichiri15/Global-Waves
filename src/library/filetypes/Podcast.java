package library.filetypes;

import commands.InputCommands;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;

import java.util.ArrayList;

public class Podcast extends AudioFile {
//    private String owner;
    private ArrayList<EpisodeInput> episodes;

    /**
     *
     * @param podcastInput
     */
    public Podcast(final PodcastInput podcastInput) {
        this.name = podcastInput.getName();
        this.owner = podcastInput.getOwner();
        this.episodes = podcastInput.getEpisodes();
    }

    /**
     *
     * @param inputCommand
     */
    public Podcast(final InputCommands inputCommand) {
        this.name = inputCommand.getName();
        this.owner = inputCommand.getUsername();
        this.episodes = inputCommand.getEpisodes();
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(final String owner) {
        this.owner = owner;
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
}
