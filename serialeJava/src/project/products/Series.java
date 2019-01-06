package project.products;

import java.io.Serializable;
import java.util.ArrayList;

import project.components.Season;

public class Series extends Product implements Serializable {
    private ArrayList<String> actors;
    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private int numberOfEpisodes;

    public Series() {
    }

    @Override
    public String toString() {
        return "Series{" + getTitle() +
                "actors=" + actors +
                ", numberOfSeasons=" + numberOfSeasons +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
    }

    @Override
    public String getType() {
        return "Series";
    }

    public void calculateDuration() {
        int totalDuration = 0;
        for (var season: seasons) {
            for (var episode: season.getEpisodes()) {
                totalDuration += episode.getDuration();
            }
        }
        this.setDuration(totalDuration);
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }
}
