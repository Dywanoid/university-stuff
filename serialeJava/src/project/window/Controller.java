package project.window;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.StringProperty;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.entities.Distributor;
import project.entities.User;
import project.entities.VOD;
import project.products.Product;

import java.util.ArrayList;

public class Controller {
    private VOD model = null;
    private String currentPanel = "start";
    private Stage stage = null;

    @FXML VBox menuPane;
    @FXML Pane contentPane;
    @FXML VBox actionPane;

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

    void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest( event -> {
            System.out.println("Closing Stage");
            model.killDistributors();
        } );

    }

    private void clearButtons() {
        controlButton.getStyleClass().clear();
        productsButton.getStyleClass().clear();
        objectsButton.getStyleClass().clear();
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
        model.newSeries();
        change();
    }
    @FXML
    private void addMovie() {
        model.newMovie();
        change();
    }
    @FXML
    private void addStream() {
        model.newStream();
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
            controlButton.getStyleClass().clear();
            controlButton.getStyleClass().add("selectedButton");
        }
    }

    @FXML
    void productsPanel() {
        if(!currentPanel.equals("products")) {
            clearButtons();
            currentPanel = "products";

            VBox mainVBOX = new VBox();
            HBox searchBarHBOX = new HBox();
            AnchorPane listAnchor = new AnchorPane();
            VBox listVBOX = new VBox();

            Label label = new Label();
            label.setText("Search: ");
            label.setPadding(new Insets(5, 5, 5, 5));

            TextField textfield = new TextField();
            textfield.setId("searchBar");
//            textfield.textProperty().addListener((observable -> ));

            Button button = new Button();
            button.setText("ok");
            button.setOnAction(this::searchHandle);


            searchBarHBOX.getChildren().addAll(label, textfield, button);
            mainVBOX.getChildren().add(searchBarHBOX);



            ArrayList<Product> products = model.getProducts();

            for (var product : products) {
                HBox productPanel = new HBox();
                productPanel.getStyleClass().add("productListItem");

                VBox labelsVBOX = new VBox();
                ImageView imageView = new ImageView();

                Image image = product.getImage();

                Label title = new Label(product.getTitle());
                Label description = new Label(product.getDescription());
                Label typeAndProductionDate = new Label(String.format("Type: %s, Prod. date: %s", product.getType(), product.getProductionDate()));
                Label score = new Label(String.format("%.2f", product.getScore()));

                imageView.setImage(image);
                labelsVBOX.getChildren().addAll(title, typeAndProductionDate, description);

                productPanel.getChildren().addAll(imageView, labelsVBOX, score);
                listVBOX.getChildren().add(productPanel);
            }
            listAnchor.getChildren().add(listVBOX);
            AnchorPane.setTopAnchor(listVBOX, 0.0);
            AnchorPane.setBottomAnchor(listVBOX, 0.0);
            AnchorPane.setLeftAnchor(listVBOX, 0.0);
            AnchorPane.setRightAnchor(listVBOX, 0.0);

            mainVBOX.getChildren().add(listAnchor);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(mainVBOX);
            productsButton.getStyleClass().clear();
            productsButton.getStyleClass().add("selectedButton");

        }
    }

    @FXML
    void objectsPanel() {
        if(!currentPanel.equals("objects")) {
            clearButtons();
            currentPanel = "objects";
            contentPane.getChildren().clear();
            objectsButton.getStyleClass().clear();
            objectsButton.getStyleClass().add("selectedButton");

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

    private void searchHandle(ActionEvent actionEvent) {
        TextField tf = (TextField) contentPane.lookup("#searchBar");
        System.out.println(tf.getText());
        ArrayList<String> names = new ArrayList<>();
        for (Product p: model.getProducts()) {
            names.add(p.getTitle());
        }
        System.out.println(names);
    }

}
