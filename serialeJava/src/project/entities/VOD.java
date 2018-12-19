package project.entities;

import project.database.VODdata;
import project.products.*;
import java.util.ArrayList;
import java.util.Random;

public class VOD {
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Distributor> distributors = new ArrayList<>();
    private VODdata data = new VODdata();

    VOD() {}

    public void newDistributor() {
        String name = data.getRandomText("distributorName");
        Distributor newDist = new Distributor(name);
        distributors.add(newDist);
        showDistributors();
    }

    public void showDistributors() {
        for(Distributor d : distributors) {
            System.out.println(d.toString());
        }
    }

    public void newProducts() {
        for (var dist: distributors) {
            if((int) ((new Random()).nextFloat() * 3) == 2)
            dist.newProduct(data);
        }
    }
}
