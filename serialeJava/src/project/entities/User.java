package project.entities;

import project.products.Product;

import java.util.Date;
import java.util.ArrayList;

public class User {
    private String name;
    private Date birthday;
    private String email;
    private String creditCardNumber;
    private String subscription;
    private ArrayList<Product> products;

    public User(String name, Date birthday, String email, String creditCardNumber, String subscription, ArrayList<Product> products) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.subscription = subscription;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
