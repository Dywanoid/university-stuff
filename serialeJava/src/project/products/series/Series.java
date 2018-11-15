package project.products.series;

import java.util.ArrayList;
import java.util.Date;
import project.entities.distributor.Distributor;
import project.products.Product;

public class Series extends Product {

    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private String genre;

    public Series(String imgPath, String title, String description, Date productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, int numberOfSeasons, ArrayList<Season> seasons, String genre) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.genre = genre;
    }

    @Override
    public String toString() {
        String poprzedni = super.toString();
        return poprzedni + "\nproject.products.series{" +
                "numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                ", genre='" + genre + '\'' +
                '}';
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

}
