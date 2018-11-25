package project.products;

import java.util.ArrayList;

import project.components.Sale;
import project.entities.Distributor;

public class Stream extends Product {
    private int date;
    private Sale sale;

    public Stream() {
    }

    public Stream(String imgPath, String title, String description, String productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, int date) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

}
