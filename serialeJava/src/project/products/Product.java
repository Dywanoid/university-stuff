package project.products;

import project.entities.Distributor;

import java.util.ArrayList;
import java.util.Date;

/**
 * Abstract class for other products: series, movie, stream
 */
public abstract class Product {
    private String imgPath;
    private String title;
    private String description;
    private Date productionDate;
    private int duration;
    private Distributor distributor;
    private ArrayList<String> countries;
    private float score;

    /**
     *
     * @param imgPath path to image
     * @param title title of product
     * @param description description of product
     * @param productionDate production date of product
     * @param duration duration of product in minutes
     * @param distributor distributor of product
     * @param countries countries in which product is available
     * @param score score of product
     */
    Product(String imgPath, String title, String description, Date productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score) {
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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public Date getProductionDate() {
        return productionDate;
    }

    public int getDuration() {
        return duration;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
