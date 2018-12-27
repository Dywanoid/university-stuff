package project.products;

import project.components.Sale;

public class Stream extends Product {
    private int date;
    private Sale sale;
    private final String type = "Stream";


    public Stream() {
    }

    @Override
    public String toString() {
        return "Stream{" +
                "date=" + date +
                ", sale=" + sale +
                '}';
    }

    @Override
    public String getType() {
        return type;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
