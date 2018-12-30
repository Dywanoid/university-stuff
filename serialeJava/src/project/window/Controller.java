package project.window;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import project.entities.VOD;
import project.products.Product;

import java.util.ArrayList;

public class Controller {
    private VOD model = null;
    private String currentPanel = "start";

    @FXML private VBox menuPane;
    @FXML private Pane contentPane;
    @FXML private VBox actionPane;
    @FXML private AnchorPane productInfoPane;

    @FXML private Button controlButton;
    @FXML private Button productsButton;
    @FXML private Button objectsButton;

    @FXML private Button newDistributorButton;
    @FXML private Button newUserButton;
    @FXML private Button newSeriesButton;
    @FXML private Button newMovieButton;
    @FXML private Button newStreamButton;

    public Controller(){}

    void setModel(VOD model) {
        this.model = model;
    }

    void setStage(Stage stage) {
        stage.setOnHidden(e -> Platform.exit());
        stage.setOnCloseRequest( event -> {
            System.out.println("Closing Stage");
            model.killDistributors();
            model.killUsers();
            model.close();
        } );

    }

    private void clearButtons() {
        controlButton.getStyleClass().clear();
        productsButton.getStyleClass().clear();
        objectsButton.getStyleClass().clear();
    }

    private void refreshScreen() {
        String current = currentPanel;
        currentPanel = "refreshScreen";
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
        refreshScreen();
    }
    @FXML
    private void addUser() {
        model.newUser();
        refreshScreen();
    }

    @FXML
    private void addSeries() {
        model.newSeries();
        refreshScreen();
    }

    @FXML
    private void addMovie() {
        model.newMovie();
        refreshScreen();
    }

    @FXML
    private void addStream() {
        model.newStream();
        refreshScreen();
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
            controlButton.getStyleClass().add("selectedButton");
        }
    }

    @FXML
    void productsPanel() {
        if(!currentPanel.equals("products")) {
            clearButtons();
            currentPanel = "products";

            VBox mainVBOX = new VBox();
            mainVBOX.setPrefWidth(400);
            HBox searchBarHBOX = new HBox();
            AnchorPane listAnchor = new AnchorPane();
            listAnchor.getChildren().add(generateProductList(""));

            Label label = new Label();
            label.setText("Search: ");
            label.setPadding(new Insets(5, 5, 5, 5));

            TextField textfield = new TextField();
            textfield.setId("searchBar");
            textfield.textProperty().addListener((observable, oldValue, newValue) ->{
                listAnchor.getChildren().clear();
                if(newValue != null && !newValue.isEmpty()) {
                    listAnchor.getChildren().add(generateProductList(newValue));
                } else {
                    listAnchor.getChildren().add(generateProductList(""));
                }
            });

            searchBarHBOX.getChildren().addAll(label, textfield);
            mainVBOX.getChildren().add(searchBarHBOX);

            mainVBOX.getChildren().add(listAnchor);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(mainVBOX);
            productsButton.getStyleClass().add("selectedButton");

        }
    }

    private boolean checkProduct(Product product, String text) {
        boolean useThisProduct = false;

        // if searched phrase is in title, use it
        if(product.getTitle().contains(text)) {
            useThisProduct = true;
        } else {
            // if searched phrase is in any actor's name, use it
            if(!product.getType().equals("stream")) {
                for (String actor: product.getActors()) {
                    if(actor.contains(text)) {
                        useThisProduct = true;
                        break;
                    }
                }
            }
        }


        return useThisProduct;
    }

    private ScrollPane generateProductList(String searchText) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scrollPane");
        ArrayList<Product> products = new ArrayList<>(model.getProducts());
        products.removeIf(prod -> !checkProduct(prod, searchText));
        VBox listVBOX = new VBox();
        listVBOX.setSpacing(5);
        for (var product : products) {
            HBox productPanel = new HBox();
            productPanel.setSpacing(10);
            productPanel.getStyleClass().add("productListItem");

            productPanel.setOnMouseClicked((MouseEvent e) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("productInfo.fxml"));
                    productInfoPane = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Product info");
                    stage.setScene(new Scene(productInfoPane, 400, 450));
                    stage.show();
                } catch (Exception ex) {
                    System.out.println("Error with loading another window!");
                }
               displayInfo(product);
            });

            VBox labelsVBOX = new VBox();
            ImageView imageView = new ImageView();

            Image image = product.getImage();

            Label title = new Label(product.getTitle());
            title.setFont(new Font("Cambria", 16));

            Label description = new Label(product.getDescription());
            Label typeAndProductionDate = new Label(String.format("Type: %s, Prod. date: %s", product.getType(), product.getProductionDate()));
            Label score = new Label(String.format("%.2f", product.getScore()));
            score.setFont(new Font("Arial", 16));

            imageView.setImage(image);
            labelsVBOX.getChildren().addAll(title, typeAndProductionDate, description);
            HBox.setHgrow(imageView, Priority.ALWAYS);
            HBox.setHgrow(labelsVBOX, Priority.ALWAYS);
            HBox.setHgrow(score, Priority.ALWAYS);
            productPanel.getChildren().addAll(imageView, labelsVBOX, score);
            listVBOX.getChildren().add(productPanel);
        }
        scrollPane.setPrefSize(325, 600);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setContent(listVBOX);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        return scrollPane;
    }

    private void displayInfo(Product product) {
        Label tescik = new Label(String.format("%d %s", product.getID(), product.getTitle()));
        HBox hbox = new HBox();

        ImageView imageView = new ImageView();
        Image image = product.getImage();
        imageView.setImage(image);
        Label title = new Label(product.getTitle());
        String typeString = product.getType();
        Label type = new Label(typeString);
        Label genre = null;
        if(!typeString.equals("stream")) {
            genre = new Label(product.getGenre());
        }
        hbox.getChildren().addAll(imageView, title, type, genre);
        productInfoPane.getChildren().add(hbox);
//        System.out.println(productInfoPane.getChildren());

    }

    @FXML
    void objectsPanel() {
        if(!currentPanel.equals("objects")) {
            clearButtons();
            currentPanel = "objects";
            contentPane.getChildren().clear();
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

}
