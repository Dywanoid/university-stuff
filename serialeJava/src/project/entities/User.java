package project.entities;

import project.products.Product;

import java.util.ArrayList;

public class User {
    private static int newUserID = 0;
    private int ID;
    private String name;
    private String birthday;
    private String email;
    private String creditCardNumber;
    private String subscription;
    private ArrayList<Product> boughtProducts;

    public User(String name, String birthday, String email, String creditCardNumber, String subscription, ArrayList<Product> products) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.subscription = subscription;
        this.boughtProducts = products;
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public ArrayList<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(ArrayList<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }
}
