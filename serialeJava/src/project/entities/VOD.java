package project.entities;

import project.database.VODdata;
import project.products.*;
import project.utils.Utilities;

import java.util.ArrayList;
import java.util.Random;

public class VOD {
    private volatile ArrayList<Product> products = new ArrayList<>();
    private volatile int nProducts = 0;
    private volatile int nSeries = 0;
    private volatile int nMovies = 0;
    private volatile int nStreams = 0;
    private volatile ArrayList<User> users = new ArrayList<>();
    private volatile int nUsers = 0;
    private ArrayList<Distributor> distributors = new ArrayList<>();
    private int nDistributors = 0;
    VODdata data = null;
    private boolean closed = false;
    private final User userMonitor = new User();

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
    synchronized (userMonitor) {
        users.add(newUser);
    }
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

    synchronized void seriesAdded() {
        nSeries++;
        nProducts++;
    }

    synchronized void movieAdded() {
        nMovies++;
        nProducts++;
    }

    synchronized void streamAdded() {
        nStreams++;
        nProducts++;
    }

    synchronized private void userAdded() {
        nUsers++;
    }

    synchronized  private void distributorAdded() {
        nDistributors++;
    }

    synchronized void seriesDeleted() {
        nSeries--;
        nProducts--;
    }

    synchronized void movieDeleted() {
        nMovies--;
        nProducts--;
    }

    synchronized void streamDeleted() {
        nStreams--;
        nProducts--;
    }

    synchronized private void userDeleted() {
        nUsers--;
    }

    synchronized private void distributorDeleted() {
        nDistributors--;
    }

    synchronized public int getnProducts() {
        return nProducts;
    }

    synchronized public int getnSeries() {
        return nSeries;
    }

    synchronized public int getnMovies() {
        return nMovies;
    }

    synchronized public int getnStreams() {
        return nStreams;
    }

    synchronized  public int getnUsers() {
        return nUsers;
    }

    synchronized public int getnDistributors() {
        return nDistributors;
    }

    synchronized public ArrayList<Product> getProducts() {
        return products;
    }

    synchronized void addProduct(Product product) {
        products.add(product);
    }

    synchronized public void killDistributors() {
        for (Distributor dist: distributors) {
            dist.kill();
        }
    }

    synchronized  public void killUsers() {
        for (User user: users) {
          user.kill();
        }
    }

    public void close() {
        closed = true;
    }

    boolean closed() {
        return closed;
    }

    synchronized void watchSomething() {
        if(products.size() > 0) products.get((int) (Math.random() * products.size())).watchThisProduct(Simulation.getSimulationTime());
    }

    synchronized void deleteProduct(Product product) {
        switch(product.getType()) {
            case "Series":
                seriesDeleted();
                break;
            case "Movie":
                movieDeleted();
                break;
            case "Stream":
                streamDeleted();
                break;
        }
        products.remove(product);
    }

    synchronized public void deleteUser(User user) {
        users.remove(user);
    }
}
