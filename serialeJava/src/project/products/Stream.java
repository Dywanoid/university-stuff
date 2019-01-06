package project.products;

import project.components.Sale;

import java.io.Serializable;

public class Stream extends Product implements Serializable {
    private int date;
    private Sale sale;

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
        return "Stream";
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
