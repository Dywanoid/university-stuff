package project.components;

import project.products.Product;
import java.util.ArrayList;

public class Sale {
    private int endDate;
    private float reduction;
    private ArrayList<Product> coveredProducts = new ArrayList<>();

    public Sale(int endDate, float reduction) {
        this.endDate = endDate;
        this.reduction = reduction;
    }

    public int getEndDate() {
        return endDate;
    }

    public float getReduction() {
        return reduction;
    }

    public void addProduct(Product product) {
        coveredProducts.add(product);
    }

    public void finishSale() {
        for (Product product: coveredProducts) {
            product.setSale(null);
        }
    }
}
