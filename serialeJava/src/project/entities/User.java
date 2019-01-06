package project.entities;

import project.components.Subscription;
import project.products.Product;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Runnable, Serializable {
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
        start();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }
    void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    Subscription getSubscription() {
        return subscription;
    }

    void setSubscription(Subscription subscription) {
        this.subscription = subscription;
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
