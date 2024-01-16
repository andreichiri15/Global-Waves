package library.user.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RevenueStats {
    private Double merchRevenue;
    private String mostProfitableSong;
    private int ranking;
    private Double songRevenue;
    private boolean isArtistLoaded;

    public RevenueStats() {
        merchRevenue = 0.0;
        mostProfitableSong = "N/A";
        songRevenue = 0.0;
        isArtistLoaded = false;
    }
    /**
     *
     * @return
     */
    public Double getMerchRevenue() {
        return merchRevenue;
    }

    /**
     *
     * @param merchRevenue
     */
    public void setMerchRevenue(final Double merchRevenue) {
        this.merchRevenue = merchRevenue;
    }

    /**
     *
     * @return
     */
    public String getMostProfitableSong() {
        return mostProfitableSong;
    }

    /**
     *
     * @param mostProfitableSong
     */
    public void setMostProfitableSong(final String mostProfitableSong) {
        this.mostProfitableSong = mostProfitableSong;
    }

    /**
     *
     * @return
     */
    public int getRanking() {
        return ranking;
    }

    /**
     *
     * @param ranking
     */
    public void setRanking(final int ranking) {
        this.ranking = ranking;
    }

    /**
     *
     * @return
     */
    public Double getSongRevenue() {
        return songRevenue;
    }

    /**
     *
     * @param songRevenue
     */
    public void setSongRevenue(final Double songRevenue) {
        this.songRevenue = songRevenue;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public boolean isArtistLoaded() {
        return isArtistLoaded;
    }


    /**
     *
     * @param artistLoaded
     */
    public void setArtistLoaded(final boolean artistLoaded) {
        isArtistLoaded = artistLoaded;
    }
}
