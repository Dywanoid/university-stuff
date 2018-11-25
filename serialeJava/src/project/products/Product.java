package project.products;

import project.entities.Distributor;

import java.util.ArrayList;

public abstract class Product {
    private String imgPath;
    private String title;
    private String description;
    private String productionDate;
    private int duration;
    private Distributor distributor;
    private ArrayList<String> countries;
    private float score;
    private float price;

    Product() {
    }

    Product(String imgPath, String title, String description, String productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score) {
        this.imgPath = imgPath;
        this.title = title;
        this.description = description;
        this.productionDate = productionDate;
        this.duration = duration;
        this.distributor = distributor;
        this.countries = countries;
        this.score = score;
    }

    @Override
    public String toString() {
        return "project.products.Product{" +
                "imgPath='" + imgPath + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", productionDate=" + productionDate +
                ", duration=" + duration +
                ", distributor=" + distributor +
                ", countries.txt=" + countries +
                ", score=" + score +
                '}';
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
}
