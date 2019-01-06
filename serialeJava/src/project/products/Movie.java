package project.products;

import java.io.Serializable;
import java.util.ArrayList;
import project.components.Sale;

public class Movie extends Product implements Serializable {
    private ArrayList<String> actors;
    private String trailerURL;
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
