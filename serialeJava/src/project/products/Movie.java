package project.products;

import java.util.ArrayList;

import project.components.Sale;
import project.entities.Distributor;

public class Movie extends Product {
    private String genre;
    private ArrayList<String> actors;
    private String trailerURL;
    private int avalibleToWatchTime;
    private Sale sale;

    public Movie() {
    }

    public Movie(String imgPath, String title, String description, String productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, String genre, ArrayList<String> actors, String trailerURL, int avalibleToWatchTime) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.genre = genre;
        this.actors = actors;
        this.trailerURL = trailerURL;
        this.avalibleToWatchTime = avalibleToWatchTime;
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

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public int getAvalibleToWatchTime() {
        return avalibleToWatchTime;
    }

    public void setAvalibleToWatchTime(int avalibleToWatchTime) {
        this.avalibleToWatchTime = avalibleToWatchTime;
    }
}
