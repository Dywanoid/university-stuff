package project.window;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import project.entities.Distributor;
import project.entities.User;
import project.entities.VOD;
import project.products.Movie;
import project.products.Product;
import project.products.Series;
import project.products.Stream;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Controller implements Serializable {
    private VOD model = null;
    private String currentPanel = "start";

    @FXML
    private Pane contentPane;
    @FXML
    private AnchorPane productInfoPane;
    @FXML
    private AnchorPane objectInfoPane;

    @FXML
    private Button controlButton;
    @FXML
    private Button productsButton;
    @FXML
    private Button objectsButton;

    public Controller() {
    }

    void setModel(VOD model) {
        this.model = model;
        model.setController(this);
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

    public void refreshScreen(boolean fromController) {

        Platform.runLater(() -> {
            String current = currentPanel;
            currentPanel = "refreshScreen";
            switch (current) {
                case "control":
                    controlPanel();
                    break;
                case "products":
                    if(fromController) productsPanel();
                    break;
                case "objects":
                    if(fromController) objectsPanel();
                    break;
            }
        });
    }

    @FXML
    private void addDist() {
        model.newDistributor();
        refreshScreen(true);
    }

    @FXML
    private void addUser() {
        model.newUser();
        refreshScreen(true);
    }

    @FXML
    private void addSeries() {
        model.newSeries();
        refreshScreen(true);
    }

    @FXML
    private void addMovie() {
        model.newMovie();
        refreshScreen(true);
    }

    @FXML
    private void addStream() {
        model.newStream();
        refreshScreen(true);
    }

    @FXML
    void controlPanel() {
        if (!currentPanel.equals("control")) {
            clearButtons();
            currentPanel = "control";

            VBox vbox = new VBox();

            Label numberOfProducts = new Label();
            String label1 = String.format("Number of products: \n  -Total: %s\n  -Series: %s\n  -Movies: %s\n  -Streams: %s\n",
                    model.getNumProducts(),
                    model.getNumSeries(),
                    model.getNumMovies(),
                    model.getNumStreams());
            numberOfProducts.setText(label1);

            Label numberOfUsers = new Label();
            numberOfUsers.setText("Number of users: " + model.getNumUsers());

            Label numberOfDistributors = new Label();
            numberOfDistributors.setText(String.format("Number of distributors: %s\n\n", model.getNumDistributors()));
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
            ObservableList<String> options = FXCollections.observableArrayList("Distributors", "Users", "Products");


            VBox vbox = new VBox();
            HBox hbox = new HBox();
            Label label = new Label("Pick: ");
            label.setPadding(new Insets(5, 5, 5, 5));

            ComboBox<? extends String> comboBox = new ComboBox<>(options);
            comboBox.setId("comboBox");
            comboBox.valueProperty().addListener((ChangeListener<String>) (ov, oldValue, newValue) -> populateList(newValue));

            ListView<? extends String> listView = new ListView<>();
            listView.setId("list-view");
            listView.getSelectionModel().selectedItemProperty().addListener(
                    (ChangeListener<String>) (ov, oldValue, newValue) -> {
                        if(newValue != null) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("objectInfo.fxml"));
                                objectInfoPane = loader.load();
                                Stage stage = new Stage();
                                stage.setTitle("Object info");
                                stage.setScene(new Scene(objectInfoPane, 400, 400));
                                stage.show();
                            } catch (Exception ex) {
                                System.out.println("Error with loading another window!");
                            }
                            displayObjectInfo(newValue);
                        }
                    });

            contentPane.getChildren().clear();
            hbox.getChildren().addAll(label, comboBox);
            hbox.setSpacing(5);
            vbox.getChildren().addAll(hbox, listView);
            vbox.setSpacing(10);
            contentPane.getChildren().add(vbox);
            objectsButton.getStyleClass().add("selectedButton");

        }
    }

    private void populateList(String chosen) {
        ObservableList<String> listViewEntries = FXCollections.observableArrayList();
        switch(chosen) {
            case "Distributors":
                for(Distributor distributor: model.getDistributors()) {
                    listViewEntries.add(String.format("ID: %s Name: %s", distributor.getID(), distributor.getName()));
                }
                break;
            case "Users":
                for(User user: model.getUsers()) {
                    listViewEntries.add(String.format("ID: %s Name: %s", user.getID(), user.getName()));
                }
                break;
            case "Products":
                for(Product product: model.getProducts()) {
                    listViewEntries.add(String.format("ID: %s Title: %s", product.getID(), product.getTitle()));
                }
                break;
        }

        @SuppressWarnings("unchecked") ListView<String> listView = (ListView<String>) contentPane.lookup("#list-view");
        listView.setItems(listViewEntries);
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
        scrollPane.setPrefViewportWidth(460);
        scrollPane.setMinViewportWidth(380);
        ArrayList<Product> products = new ArrayList<>(model.getProducts());
        products.removeIf(prod -> !checkProduct(prod, searchText));
        ArrayList<Product> productsToDisplay = products.size() > 100 ? new ArrayList<>(products.subList(0, 100)) : products;
        VBox listVBOX = new VBox();
        listVBOX.setPrefSize(360, 600);
        listVBOX.setSpacing(5);
        for (Product product : productsToDisplay) {
            HBox productPanel = new HBox();
            productPanel.setSpacing(10);
            productPanel.getStyleClass().add("productListItem");

            productPanel.setOnMouseClicked((MouseEvent e) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("productInfo.fxml"));
                    productInfoPane = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Product info");
                    double height = product.getViewData().size() > 0 ? 650 : 280;
                    stage.setScene(new Scene(productInfoPane, 600, height));
                    stage.show();
                } catch (Exception ex) {
                    System.out.println("Error with loading another window!");
                }
                displayProductInfo(product);
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
        refreshScreen(true);
    }

    private void displayProductInfo(Product product) {
        Map<Integer, Integer> data = product.getViewData();

        VBox mainVBox = new VBox();
        VBox topLabelsVBox = new VBox();
        VBox uniqueLabels = new VBox();
        VBox labelsVBox = new VBox();
        HBox topHBox = new HBox();

        ImageView imageView = new ImageView();
        Image image = product.getImage();
        imageView.setImage(image);
        Label titleLabel = new Label(product.getTitle());
        String typeString = product.getType();
        Label typeLabel = new Label("Type: " + typeString);
        Label genreLabel = new Label("Genre: " + product.getGenre());
        Label scoreLabel = new Label("Score: " + String.valueOf(product.getScore()));
        Label descriptionLabel = new Label("Description: " + product.getDescription());
        Label productionDateLabel = new Label("Production date: " + product.getProductionDate());
        Label durationLabel = new Label("Duration: " + String.valueOf(product.getDuration()));
        Label distributorLabel = new Label("Distributor: " + product.getDistributor().getName());
        Label countriesLabel = new Label("Countries: " + product.getCountries().toString());
        Label priceLabel = new Label("Price: " + String.valueOf(product.getPrice()));


        switch(typeString) {
            case "Series":
                uniqueLabels.getChildren().addAll(
                        new Label("Number of seasons: " + ((Series) product).getNumberOfSeasons()),
                        new Label("Number of episodes: "+ ((Series) product).getNumberOfEpisodes())
                );
                break;
            case "Movie" :
                Hyperlink url = new Hyperlink("Trailer URL");
                url.setOnAction(actionEvent -> {
                    try {
                        Desktop.getDesktop().browse(new URL(((Movie) product).getTrailerURL()).toURI());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                });
                uniqueLabels.getChildren().add(url);

                break;
            case "Stream":
                uniqueLabels.getChildren().add(new Label("Event date: " + ((Stream) product).getDate()));
                break;
        }


        topLabelsVBox.getChildren().addAll(titleLabel, typeLabel, descriptionLabel);

        labelsVBox.setSpacing(5);
        labelsVBox.getChildren().addAll(
                scoreLabel,
                genreLabel,
                productionDateLabel,
                durationLabel,
                distributorLabel,
                countriesLabel,
                priceLabel
        );

        topHBox.getChildren().addAll(imageView, topLabelsVBox, uniqueLabels);
        mainVBox.getChildren().addAll(topHBox, labelsVBox);


        if(data.size() > 0) {
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Time of simulation");
            yAxis.setLabel("Times watched");

            final LineChart<Number, Number> lineChart =
                    new LineChart<>(xAxis, yAxis);

            lineChart.setTitle("watched over time");

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Product watched over time");
            int maximumTime = Collections.max(data.keySet());
            for (int t = 0; t <= maximumTime; t++) {
                Integer value = data.get(t);

                series.getData().add(new XYChart.Data<>(t, value != null ? value : 0));
            }

            lineChart.getData().add(series);
            mainVBox.getChildren().add(lineChart);
        }
        productInfoPane.getChildren().add(mainVBox);
    }

    private void eventHelperForObjectInfo(Button button, String type) {
        refreshScreen(true);
        ((Stage) button.getScene().getWindow()).close();
        populateList(type);
    }

    private void displayObjectInfo(String idName) {
        String stringID = idName.substring(4, 4 + idName.substring(4).indexOf(" "));
        int id = Integer.parseInt(stringID);
        String type = (String)((ComboBox) contentPane.lookup("#comboBox")).getValue();
        switch(type) {
            case "Distributors":
                Distributor distributor = model.getDistributor(id);

                HBox distributorHBox = new HBox();
                VBox distributorVBox = new VBox();

                Label dID = new Label("ID: " + stringID);
                Label distributorName = new Label("Name: " + distributor.getName());
                Label nProducts = new Label(String.format("Number of products: %d", distributor.numberOfProducts()));
                Button distributorButton = new Button("Delete this distributor!");
                distributorButton.setOnAction(actionEvent -> {
                    distributor.deleteMe();
                    eventHelperForObjectInfo(distributorButton, type);
                });

                distributorVBox.getChildren().addAll(dID, distributorName, nProducts);
                distributorHBox.getChildren().addAll(distributorVBox, distributorButton);

                objectInfoPane.getChildren().clear();
                objectInfoPane.getChildren().add(distributorHBox);
                break;
            case "Users":
                User user = model.getUser(id);

                HBox userHBox = new HBox();
                VBox userVBox = new VBox();

                Label uID = new Label("ID: " + stringID);
                Label userName = new Label("Name: " + user.getName());
                Label userBirthday = new Label("Birthday: " + user.getBirthday());
                Label userEmail = new Label("Email: " + user.getEmail());
                Label userCreditCardNumber = new Label("Credit card number: \n  " + user.getCreditCardNumber());
                Button userButton = new Button("Delete this user!");
                userButton.setOnAction(actionEvent -> {
                    user.deleteMe();
                    eventHelperForObjectInfo(userButton, type);

                });

                userVBox.getChildren().addAll(uID, userName, userBirthday, userEmail, userCreditCardNumber);
                userHBox.getChildren().addAll(userVBox, userButton);

                objectInfoPane.getChildren().clear();
                objectInfoPane.getChildren().add(userHBox);
                break;
            case "Products":
                Product product = model. getProduct(id);

                HBox productHBox = new HBox();
                VBox productVBox = new VBox();

                Label pID = new Label("ID: " + stringID);
                Label productName = new Label("Title: " + product.getTitle());
                Label productProductionDate = new Label("Production date: " + product.getProductionDate());
                Label productDuration = new Label("Duration: " + product.getDuration());
                Button productButton = new Button("Delete this product!");
                productButton.setOnAction(actionEvent -> {
                    product.deleteMe();
                    eventHelperForObjectInfo(productButton, type);

                });

                productVBox.getChildren().addAll(pID, productName, productProductionDate, productDuration);
                productHBox.getChildren().addAll(productVBox, productButton);

                objectInfoPane.getChildren().clear();
                objectInfoPane.getChildren().add(productHBox);
                break;
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

