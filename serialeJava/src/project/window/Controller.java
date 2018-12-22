package project.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.entities.Distributor;
import project.entities.User;
import project.entities.VOD;
import project.products.Product;

import java.util.ArrayList;

public class Controller {
    private VOD model = null;
    private String currentPanel = "start";

    @FXML Pane menuPane;
    @FXML Pane contentPane;
    @FXML Pane actionPane;

    @FXML Button controlButton;
    @FXML Button productsButton;
    @FXML Button objectsButton;

    @FXML Button newDistributorButton;
    @FXML Button newUserButton;
    @FXML Button newSeriesButton;
    @FXML Button newMovieButton;
    @FXML Button newStreamButton;


    public Controller(){}

    void setModel(VOD model) {
        this.model = model;
    }

    private void clearButtons() {
        String style = "-fx-background-color: #F0F0F0";
        controlButton.setStyle(style);
        productsButton.setStyle(style);
        objectsButton.setStyle(style);

    }

    private void change() {
        String current = currentPanel;
        currentPanel = "change";
        switch (current) {
            case "control":
                controlPanel();
                break;
            case "products":
                productsPanel();
                break;
            case "objects":
                objectsPanel();
                break;
        }
    }

    @FXML
    private void addDist() {
        model.newDistributor();
        change();
    }
    @FXML
    private void addUser() {
        model.newUser();
        change();
    }
    @FXML
    private void addSeries() {
        model.newDistributor();
        change();
    }
    @FXML
    private void addMovie() {
        model.newDistributor();
        change();
    }
    @FXML
    private void addStream() {
        model.newDistributor();
        change();
    }

    @FXML
    void controlPanel() {
        if(!currentPanel.equals("control")) {
            clearButtons();
            currentPanel = "control";

            VBox vbox = new VBox();

            Label numberOfProducts = new Label();
            String label1 = String.format("Number of products: \n  -Total: %s\n  -Series: %s\n  -Movies: %s\n  -Streams: %s\n",
                    model.getnProducts(),
                    model.getnSeries(),
                    model.getnMovies(),
                    model.getnStreams());
            numberOfProducts.setText(label1);

            Label numberOfUsers = new Label();
            numberOfUsers.setText("Number of users: " + model.getnUsers());

            Label numberOfDistributors = new Label();
            numberOfDistributors.setText("Number of distributors: " + model.getnDistributors());
            vbox.getChildren().addAll(numberOfProducts, numberOfUsers, numberOfDistributors);


            contentPane.getChildren().clear();
            contentPane.getChildren().add(vbox);
            controlButton.setStyle("-fx-background-color: #E3E3E3");
        }
    }

    @FXML
    void productsPanel() {
        if(!currentPanel.equals("products")) {
            clearButtons();
            currentPanel = "products";
            contentPane.getChildren().clear();
            productsButton.setStyle("-fx-background-color: #E3E3E3");

        }
    }

    @FXML
    void objectsPanel() {
        if(!currentPanel.equals("objects")) {
            clearButtons();
            currentPanel = "objects";
            contentPane.getChildren().clear();
            objectsButton.setStyle("-fx-background-color: #E3E3E3");

        }
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
