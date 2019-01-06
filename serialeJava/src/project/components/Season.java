package project.components;

import java.io.Serializable;
import java.util.ArrayList;

public class Season implements Serializable {
    private ArrayList<Episode> episodes;
    private int whichSeason;

    public Season(ArrayList<Episode> episodes, int whichSeason) {
            this.episodes = episodes;
            this.whichSeason = whichSeason;
    }

    @Override
    public String toString() {
        return "Season{" +
                "episodes=" + episodes +
                ", whichSeason=" + whichSeason +
                '}';
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
}
