package project.products;

import javafx.scene.image.Image;
import project.entities.Distributor;

import java.util.ArrayList;

public abstract class Product {
    private Image image;
    private String title;
    private String description;
    private String productionDate;
    private int duration;
    private Distributor distributor;
    private ArrayList<String> countries;
    private float score;
    private float price;
    private final String type = "Product";

    Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", duration=" + duration +
                ", distributor=" + distributor +
                ", countries=" + countries +
                ", score=" + score +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<String> getActors() {
        return new ArrayList<String>();
    }
    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getGenre() {
        return "";
    }
}
