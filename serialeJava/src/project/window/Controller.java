package project.window;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import project.components.Subscription;
import project.database.VODdata;
import project.entities.VOD;
import project.products.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Controller {
    private VOD model = null;
    private String currentPanel = "start";


    @FXML
    private VBox menuPane;
    @FXML
    private Pane contentPane;
    @FXML
    private VBox actionPane;
    @FXML
    private AnchorPane productInfoPane;

    @FXML
    private Button controlButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button objectsButton;

    @FXML
    private Button newDistributorButton;
    @FXML
    private Button newUserButton;
    @FXML
    private Button newSeriesButton;
    @FXML
    private Button newMovieButton;
    @FXML
    private Button newStreamButton;

    public Controller() {
    }

    void setModel(VOD model) {
        this.model = model;
    }

    void setStage(Stage stage) {
        stage.setOnHidden(e -> Platform.exit());
        stage.setOnCloseRequest(event -> {
            System.out.println("Closing Stage");
            model.killDistributors();
            model.killUsers();
            model.close();
        });

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
        if (!currentPanel.equals("control")) {
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
            numberOfDistributors.setText(String.format("Number of distributors: %s\n\n", model.getnDistributors()));
            Label moneyLabel = new Label(String.format("Money in bank: %.2f\n\n", model.getMoney()));

            Map<String, Subscription> map = VODdata.getSubscriptions();
            HBox prices = new HBox();
            VBox pricesLabels = new VBox();
            VBox pricesTextFields = new VBox();

            Label subLabel1 = new Label(String.format("Price of Basic subscription: %.2f", map.get("Basic").getPrice()));
            Label subLabel2 = new Label(String.format("Price of Family subscription: %.2f", map.get("Family").getPrice()));
            Label subLabel3 = new Label(String.format("Price of Premium subscription: %.2f", map.get("Premium").getPrice()));
            pricesLabels.getChildren().addAll(subLabel1, subLabel2, subLabel3);
            pricesLabels.setSpacing(10);

            TextField subTF1 = new TextField();
            subTF1.setId("tf1");
            TextField subTF2 = new TextField();
            subTF2.setId("tf2");
            TextField subTF3 = new TextField();
            subTF3.setId("tf3");

            pricesTextFields.getChildren().addAll(subTF1, subTF2, subTF3);


            Button pricesButton = new Button("change prices");
            pricesButton.setPadding(new Insets(30, 26, 26, 26));
            pricesButton.setOnAction(actionEvent -> changePrices());


            prices.getChildren().addAll(pricesLabels, pricesTextFields, pricesButton);
            prices.setSpacing(10);
            vbox.getChildren().addAll(numberOfProducts, numberOfUsers, numberOfDistributors, moneyLabel, prices);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(vbox);
            controlButton.getStyleClass().add("selectedButton");
        }
    }

    private void changePrices() {
        String text;

        text = ((TextField) (contentPane.lookup("#tf1"))).getCharacters().toString();
        if(!text.isEmpty()) {
            VODdata.changeSubscriptionPrice("Basic", Float.parseFloat(text));
        }

        text = ((TextField) (contentPane.lookup("#tf2"))).getCharacters().toString();
        if(!text.isEmpty()) {
            VODdata.changeSubscriptionPrice("Family", Float.parseFloat(text));
        }

        text = ((TextField) (contentPane.lookup("#tf3"))).getCharacters().toString();
        if(!text.isEmpty()) {
            VODdata.changeSubscriptionPrice("Premium", Float.parseFloat(text));
        }
        refreshScreen();
    }

    @FXML
    void productsPanel() {
        if (!currentPanel.equals("products")) {
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
            textfield.textProperty().addListener((observable, oldValue, newValue) -> {
                listAnchor.getChildren().clear();
                if (newValue != null && !newValue.isEmpty()) {
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

    @FXML
    void objectsPanel() {
        if (!currentPanel.equals("objects")) {
            clearButtons();
            currentPanel = "objects";
            contentPane.getChildren().clear();
            objectsButton.getStyleClass().add("selectedButton");

        }
    }

    private boolean checkProduct(Product product, String text) {
        boolean useThisProduct = false;

        // if searched phrase is in title, use it
        if (product.getTitle().contains(text)) {
            useThisProduct = true;
        } else {
            // if searched phrase is in any actor's name, use it
            if (!product.getType().equals("stream")) {
                for (String actor : product.getActors()) {
                    if (actor.contains(text)) {
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
                    stage.setScene(new Scene(productInfoPane, 550, 450));
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
        Map<Integer, Integer> data = product.getViewData();

        VBox vbox = new VBox();
        HBox hbox = new HBox();

        ImageView imageView = new ImageView();
        Image image = product.getImage();
        imageView.setImage(image);
        Label title = new Label(product.getTitle());
        String typeString = product.getType();
        Label type = new Label(typeString);
        Label genre = null;
        if (!typeString.equals("stream")) {
            genre = new Label(product.getGenre());
        }
        hbox.getChildren().addAll(imageView, title, type, genre);
        vbox.getChildren().add(hbox);
        if(data.size() > 0) {
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time of simulation");
            yAxis.setLabel("Times watched");

            final LineChart<Number, Number> lineChart =
                    new LineChart<>(xAxis, yAxis);

            lineChart.setTitle("watched over time");

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Product's info");
            int maximumTime = Collections.max(data.keySet());
            for (int t = 0; t <= maximumTime; t++) {
                Integer value = data.get(t);

                series.getData().add(new XYChart.Data<>(t, value != null ? value : 0));
            }

            lineChart.getData().add(series);
            vbox.getChildren().add(lineChart);
        }
        productInfoPane.getChildren().add(vbox);
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

