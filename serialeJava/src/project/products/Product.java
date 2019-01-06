package project.products;

import javafx.scene.image.Image;
import project.components.Sale;
import project.entities.Distributor;
import project.entities.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Product implements Serializable {
    private static volatile int freeID = 0;
    private int ID;
    private Image image;
    private String title;
    private String description;
    private String productionDate;
    private String genre;
    private int duration;
    private Distributor distributor;
    private ArrayList<String> countries;
    private float score;
    private float price;
    private volatile Map<Integer, Integer> viewData = new HashMap<>();
    private volatile ArrayList<User> usersOwning = new ArrayList<>();
    private Sale sale = null;

    Product() {
        setSafeID();
    }

    private synchronized void setSafeID() {
        this.ID = freeID++;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", viewData=" + viewData +
                '}';
    }

    public Sale getSale() {return sale;}

    public void setSale(Sale sale) {}

    public int getID() {
        return ID;
    }

    public Map<Integer, Integer> getViewData() {
        return viewData;
    }

    /**
     * Watch this product in specific time
     * @param time time in which this product is being watched
     */
    public synchronized void watchThisProduct(int time) {
        // if there is no value for this specific time it will be 1 for now, if it exists it will be incremented
        viewData.merge(time, 1, (a, b) -> a + b);
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<String> getActors() {
        return new ArrayList<>();
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

    public void setGenre(String genre) {
        this.genre = genre;
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
        return "Product";
    }

    public String getGenre() {
        return genre;
    }

    /**
     * Adding user to usersOwning ArrayList
     * @param user user who bought this product
     */
    synchronized public void addUser(User user) {
        usersOwning.add(user);
    }

    /**
     * Deleting this product from everywhere
     */
    public void deleteMe() {
        distributor.deleteProductFromMe(this);
        distributor.deleteProductFromVOD(this);
        unownUsers();

    }

    /**
     * Deleting this product from users who bought them
     */
    public void unownUsers() {
        for (User user: usersOwning) {
            user.deleteProduct(this);
        }
    }
}
