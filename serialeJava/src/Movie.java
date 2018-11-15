import java.util.ArrayList;
import java.util.Date;

public class Movie extends Product{
    private String genre;
    private ArrayList<String> actors;
    private String trailerURL;
    private float price;
    private int avalibleToWatchTime; // TODO: change this (something like time type?)

    public Movie(String imgPath, String title, String description, Date productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, String genre, ArrayList<String> actors, String trailerURL, float price, int avalibleToWatchTime) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.genre = genre;
        this.actors = actors;
        this.trailerURL = trailerURL;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAvalibleToWatchTime() {
        return avalibleToWatchTime;
    }

    public void setAvalibleToWatchTime(int avalibleToWatchTime) {
        this.avalibleToWatchTime = avalibleToWatchTime;
    }
}
