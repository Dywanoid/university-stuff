package project.products;

import java.util.ArrayList;
import java.util.Date;

import project.entities.Distributor;
import project.components.Season;

public class Series extends Product {
    private String genre;
    private float price;
    private ArrayList<String> actors;
    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private int numberOfEpisodes;

    public Series(String imgPath, String title, String description, Date productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, String genre, float price, ArrayList<String> actors, int numberOfSeasons, ArrayList<Season> seasons, int numberOfEpisodes) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.genre = genre;
        this.price = price;
        this.actors = actors;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.numberOfEpisodes = numberOfEpisodes;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSeries{" +
                "genre='" + genre + '\'' +
                ", price=" + price +
                ", actors=" + actors +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
