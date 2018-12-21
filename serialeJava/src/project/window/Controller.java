package project.window;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import project.entities.Distributor;
import project.entities.User;
import project.entities.VOD;
import project.products.Product;

import java.util.ArrayList;

public class Controller {
    private VOD model = null;
    private String currentPanel = "control";
    @FXML Pane contentPane;

    public Controller(){}

    void setModel(VOD model) {
        this.model = model;
    }

    @FXML
    private void addDist() {
        model.newDistributor();
    }

    @FXML
    void controlPanel() {
        if(!currentPanel.equals("control")) {
            currentPanel = "control";
            ArrayList<Product> products = model.getProducts();
            ArrayList<User> users = model.getUsers();
            ArrayList<Distributor> distributors = model.getDistributors();

            System.out.println("number of products: " + products.size());
            System.out.println("number of users: " + users.size());
            System.out.println("number of distributors: " + distributors.size());
        }
    }

    @FXML
    void productsPanel() {
        if(!currentPanel.equals("products")) {
            currentPanel = "products";
        }
        System.out.println("products panel" + currentPanel);
    }

    @FXML
    void objectsPanel() {
        if(!currentPanel.equals("objects")) {
            currentPanel = "objects";
        }
        System.out.println("objects panel" + currentPanel);
    }

    @FXML
    private void saveSimulation() {
        System.out.println("saved");
    }

    @FXML
    private void loadSimulation() {
        System.out.println("loaded");
    }
}
