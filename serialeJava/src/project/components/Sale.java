package project.components;

import project.products.Product;
import java.util.ArrayList;

public class Sale {
    private int startDate;
    private int endDate;
    private float reduction;
    private ArrayList<Product> coveredProducts;

    public Sale(int startDate, int endDate, float reduction) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reduction = reduction;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public float getReduction() {
        return reduction;
    }
}
