package project.entities;

import project.components.Subscription;
import project.products.Product;

import java.util.ArrayList;

public class User implements Runnable{
    private static int newUserID = 0;
    private int ID;
    private String name;
    private String birthday;
    private String email;
    private String creditCardNumber;
    private Subscription subscription;
    private ArrayList<Product> boughtProducts = new ArrayList<>();
    private VOD VODpointer;
    private boolean alive = true;

    // for monitor
    User() {}

    User(String name, VOD pointer) {
        this.ID = newUserID++;
        this.name = name;
        this.VODpointer = pointer;
        Thread thread = new Thread(this);
        thread.start();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public ArrayList<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void deleteProduct(Product product) {
        boughtProducts.remove(product);
    }

    public int getID() {
        return ID;
    }

    @Override
    public void run() {
        while(alive) {
            long sleepTime = (long) (1000 * Math.random() * 4);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(alive) {
                Product product = VODpointer.watchSomething();
                if(product != null && subscription == null && !boughtProducts.contains(product)) {
                    VODpointer.payForProduct(product);
                    boughtProducts.add(product);
                    product.addUser(this);
                }
            }

        }
    }

    void kill() {
        alive = false;
    }

    public void deleteMe() {
        kill();
        VODpointer.deleteUser(this);
    }
}
