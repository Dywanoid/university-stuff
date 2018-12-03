package project.products;

import java.util.ArrayList;

import project.components.Season;

public class Series extends Product {
    private String genre;
    private ArrayList<String> actors;
    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private int numberOfEpisodes;

    public Series() {
    }

    @Override
    public String toString() {
        return "Series "+ getTitle() +" {" +
                "genre='" + genre + '\'' +
                ", actors=" + actors +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
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

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
