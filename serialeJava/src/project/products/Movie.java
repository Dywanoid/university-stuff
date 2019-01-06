package project.products;

import java.util.ArrayList;
import project.components.Sale;

public class Movie extends Product {
    private String genre;
    private ArrayList<String> actors;
    private String trailerURL;
    private int avalibleToWatchTime;
    private Sale sale;

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                getViewData() + '\'' +
                '}';
    }

    @Override
    public String getType() {
        return "Movie";
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
