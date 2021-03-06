package project.entities;

import project.components.License;
import project.components.Sale;
import project.components.Subscription;
import project.database.VODdata;
import project.products.*;
import project.utils.Utilities;
import project.window.Controller;

import java.io.Serializable;
import java.util.ArrayList;

public class VOD implements Serializable {
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
    private volatile float money = 0;
    private Controller controller;

    public void init() {
        data = new VODdata();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Refresh screen
     */
    void refresh() {
        controller.refreshScreen(false);
    }

    VOD() {}

    /**
     * New Distributor is added
     */
    public void newDistributor() {
        String name = data.getRandomText("distributorName");
        Distributor newDist = new Distributor(name, this);
        distributors.add(newDist);
        distributorAdded();
    }

    /**
     * New User is added
     */
    public void newUser() {
    String name = String.format("%s %s",data.getRandomText("name1"), data.getRandomText("name2"));
    User newUser = new User(name, this);

    String birthday = Utilities.getRandomDate(-365 * 60, -365*18);
    newUser.setBirthday(birthday);

    String email = String.format("%s@%s",data.getRandomText("email1"), data.getRandomText("email2"));
    newUser.setEmail(email);

    newUser.setCreditCardNumber(String.format("%d %d %d %d",
            Utilities.getRandomInt(1000, 9999),
            Utilities.getRandomInt(1000, 9999),
            Utilities.getRandomInt(1000, 9999),
            Utilities.getRandomInt(1000, 9999)));

    newUser.setSubscription(data.getRandomSubscription());
    synchronized (userMonitor) {
        users.add(newUser);
    }
    userAdded();
    }

    /**
     * New Series is added
     */
    public void newSeries() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newSeries(data);
        }

    }

    /**
     * New Movie is added
     */
    public void newMovie() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newMovie(data);
        }
    }

    /**
     * New Stream is added
     */
    public void newStream() {
        if(distributors.size() > 0) {
            distributors.get(Utilities.getRandomInt(0, distributors.size() - 1)).newStream(data);
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

    synchronized private void seriesDeleted() {
        nSeries--;
        nProducts--;
    }

    synchronized private void movieDeleted() {
        nMovies--;
        nProducts--;
    }

    synchronized private void streamDeleted() {
        nStreams--;
        nProducts--;
    }

    /**
     * Paying for Product not owned by User who doesn't have Subscription
     * @param product Product being bought
     */
    synchronized void payForProduct(Product product) {
        float price = product.getPrice();
        float reduction = 1;
        Sale sale = product.getSale();
        if(sale != null) reduction -= sale.getReduction();
        money += price * reduction;
        refresh();
    }

    /**
     * Getting money from Subscriptions
     */
    synchronized void takeSubscriptionMoney() {
        for (User user: users) {
            Subscription subscription = user.getSubscription();
            if(subscription!= null) {
                money += subscription.getPrice() ;
            }
        }
        refresh();
    }

    synchronized private void userDeleted() {
        nUsers--;
    }

    synchronized private void distributorDeleted() {
        nDistributors--;
    }

    synchronized public int getNumProducts() {
        return nProducts;
    }

    synchronized public int getNumSeries() {
        return nSeries;
    }

    synchronized public int getNumMovies() {
        return nMovies;
    }

    synchronized public int getNumStreams() {
        return nStreams;
    }

    synchronized  public int getNumUsers() {
        return nUsers;
    }

    synchronized public int getNumDistributors() {
        return nDistributors;
    }

    synchronized public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public Distributor getDistributor(int id) {
        for (Distributor distributor: distributors) {
            if(distributor.getID() == id) {
                return distributor;
            }
        }
        return null;
    }

    public User getUser(int id) {
        for (User user: users) {
            if(user.getID() == id) {
                return user;
            }
        }
        return null;
    }

    public Product getProduct(int id) {
        for (Product product: products) {
            if(product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    synchronized void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Killing all Distributor threads
     */
    synchronized public void killDistributors() {
        for (Distributor dist: distributors) {
            dist.kill();
        }
    }

    /**
     * Killing all User threads
     */
    synchronized  public void killUsers() {
        for (User user: users) {
          user.kill();
        }
    }

    /**
     * Close VOD - kill thread
     */
    public void close() {
        closed = true;
    }

    boolean closed() {
        return closed;
    }

    /**
     * Watch random Product from VOD
     * @return Product if being watched, null instead
     */
    synchronized Product watchSomething() {
        if(products.size() > 0) {
            Product randProduct = products.get((int) (Math.random() * products.size()));
            randProduct.watchThisProduct(Simulation.getSimulationTime());
            randProduct.getDistributor().watchedProduct();
            return randProduct;
        }
        return null;
    }

    /**
     * Delete specific Product from VOD
     * @param product Product that will be deleted
     */
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

    /**
     * Delete User from VOD
     * @param user User that will be deleted
     */
    synchronized void deleteUser(User user) {
        users.remove(user);
        userDeleted();
    }

    /**
     * Delete Distributor from VOD
     * @param distributor Distributor that will be deleted
     */
    synchronized void deleteDistributor(Distributor distributor) {
        distributors.remove(distributor);
        distributorDeleted();
    }

    synchronized public float getMoney() {
        return money;
    }

    /**
     * Paying distributors money depending on License
     */
    synchronized void pay() {
        for(Distributor distributor: distributors) {
            License license = distributor.getLicense();
            if(license != null) {
                if(license.isMonthly()) {
                    money -= license.getMonthlyFee();
                } else {
                    money -= license.getFee() * distributor.getWatched();
                    distributor.clearWatched();
                }
                refresh();
            }
        }
    }
}
