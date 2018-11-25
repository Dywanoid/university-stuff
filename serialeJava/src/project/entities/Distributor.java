package project.entities;

import java.util.ArrayList;
import java.util.Random;

import project.database.VODdata;
import project.products.*;

public class Distributor {
    private static int numberOfDistributors = 0;
    private String name;
    private int ID;
    private ArrayList<Product> products = new ArrayList<>();

    public Distributor(String name) {
        this.name = name;
        this.ID = numberOfDistributors++;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                '}';
    }

    public void newProduct(VODdata data) {
        Product product = null;
        int wynik = (int) ((new Random()).nextFloat() * 3);
        System.out.println(wynik);
        switch (wynik) {
            case 0:
                product = new Series();
                generateSeries(data, product);
                break;
            case 1:
                product = new Movie();
                generateMovie(data, product);
                break;
            case 2:
                product = new Stream();
                generateStream(data, product);
                break;
        }
        products.add(product);
    }

    private void generateProduct(VODdata data, Product product) {
        product.setImgPath("empty");
        product.setTitle(data.getRandomText("title"));
        product.setDescription(data.getRandomText("description"));
//        product.setProductionDate(Utilities.getRandomDate());
    }

    private void generateSeries(VODdata data, Product product) {
        generateProduct(data, product);
    }

    private void generateMovie(VODdata data, Product product) {
        generateProduct(data, product);
    }

    private void generateStream(VODdata data, Product product) {
        generateProduct(data, product);
    }

    public static int getNumberOfDistributors() {
        return numberOfDistributors;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
