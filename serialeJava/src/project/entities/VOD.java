package project.entities;

import project.database.VODdata;
import project.products.*;
import java.util.ArrayList;

public class VOD {
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Distributor> distributors = new ArrayList<>();
    private VODdata data = new VODdata();

    void start() {
        System.out.println("start");
    }

    public void newDistributor() {
        String name = data.getRandomText("distributorName");
        Distributor newDist = new Distributor(name);
        distributors.add(newDist);
    }

    public void showDistributors() {
        for(Distributor d : distributors) {
            System.out.println(d.toString());
        }
    }

    void newProduct() {

    }
}
