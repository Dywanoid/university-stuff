package project.entities;

import project.database.VODdata;
import project.products.*;
import project.utils.Utilities;

import java.util.ArrayList;
import java.util.Random;

public class VOD {
    private volatile ArrayList<Product> products = new ArrayList<>();
    private int nProducts = 0;
    private int nSeries = 0;
    private int nMovies = 0;
    private int nStreams = 0;
    private ArrayList<User> users = new ArrayList<>();
    private int nUsers = 0;
    private ArrayList<Distributor> distributors = new ArrayList<>();
    private int nDistributors = 0;
    VODdata data = null;

    public void init() {
        data = new VODdata();
    }

    VOD() {}

    public void newDistributor() {
        String name = data.getRandomText("distributorName");
        Distributor newDist = new Distributor(name, this);
        distributors.add(newDist);
        distributorAdded();
    }

    public void newUser() {
    String name = String.format("%s %s",data.getRandomText("name1"), data.getRandomText("name2"));
    User newUser = new User(name, this);

    String birthday = Utilities.getRandomDate(-365 * 60, -365*18);
    newUser.setBirthday(birthday);

    String email = String.format("%s@%s",data.getRandomText("email1"), data.getRandomText("email2"));
    newUser.setEmail(email);

    newUser.setCreditCardNumber("");

    newUser.setSubscription("");
    users.add(newUser);
    userAdded();

    }

    public void newSeries() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newSeries(data);
        }
    }

    public void newMovie() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newMovie(data);
        }
    }

    public void newStream() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newStream(data);
        }
    }

    public void newRandomProducts() {
        for (var dist: distributors) {
            if((int) ((new Random()).nextFloat() * 3) == 2)
            dist.newRandomProduct(data);
        }
    }

    void seriesAdded() {
        nSeries++;
        nProducts++;
    }

    void movieAdded() {
        nMovies++;
        nProducts++;
    }

    void streamAdded() {
        nStreams++;
        nProducts++;
    }

    private void userAdded() {
        nUsers++;
    }

    private void distributorAdded() {
        nDistributors++;
    }

    public int getnProducts() {
        return nProducts;
    }

    public int getnSeries() {
        return nSeries;
    }

    public int getnMovies() {
        return nMovies;
    }

    public int getnStreams() {
        return nStreams;
    }

    public int getnUsers() {
        return nUsers;
    }

    public int getnDistributors() {
        return nDistributors;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    synchronized void addProduct(Product product) {
        products.add(product);
    }

    public void killDistributors() {
        for (Distributor dist: distributors) {
            dist.kill();
        }
    }

    public void killUsers() {
        for (User user: users) {
          user.kill();
        }
    }
}
