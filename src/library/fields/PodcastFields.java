package library.fields;

import library.filetypes.Podcast;

import java.util.ArrayList;

public class PodcastFields {
    private String name;
    private ArrayList<String> episodes;

    public PodcastFields(final Podcast podcast) {
        this.name = podcast.getName();
        episodes = new ArrayList<>();
        for (int i = 0; i < podcast.getEpisodes().size(); i++) {
            episodes.add(podcast.getEpisodes().get(i).getName());
        }
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
    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    /**
     *
     * @param episodes
     */
    public void setEpisodes(final ArrayList<String> episodes) {
        this.episodes = episodes;
    }
}
