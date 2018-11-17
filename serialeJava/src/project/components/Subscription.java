package project.components;

public class Subscription implements java.io.Serializable{
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
        return "Subscription{" +
                "price=" + price +
                ", numberOfDevices=" + numberOfDevices +
                ", maxResolution='" + maxResolution + '\'' +
                '}';
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
