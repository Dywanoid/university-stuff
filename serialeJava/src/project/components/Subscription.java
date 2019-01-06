package project.components;

import java.io.Serializable;

public class Subscription implements Serializable {
    private float price;
    private int numberOfDevices;
    private String maxResolution;

    public Subscription(float price, int numberOfDevices, String maxResolution) {
        this.price = price;
        this.numberOfDevices = numberOfDevices;
        this.maxResolution = maxResolution;
    }

    @Override
    public String toString() {
        return "Subscription{" + "price=" + price + '}';
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }

    public String getMaxResolution() {
        return maxResolution;
    }
}
