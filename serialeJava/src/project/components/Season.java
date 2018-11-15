package project.components;

import java.util.ArrayList;

public class Season {
    private ArrayList<Episode> episodes;
    private int whichSeason;

    public Season() {
        this.episodes = new ArrayList<>();
        this.whichSeason = 1;
    }

    public Season(ArrayList<Episode> episodes, int whichSeason) {
            this.episodes = episodes;
            this.whichSeason = whichSeason;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(Episode episode) {
        this.episodes.add(episode);
    }

}
