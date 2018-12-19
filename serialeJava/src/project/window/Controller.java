package project.window;

import javafx.fxml.FXML;
import project.entities.VOD;

public class Controller {
    private VOD model = null;

    public Controller(){}

    void setModel(VOD model) {
        this.model = model;
    }

    @FXML
    private void clickXD() {
        System.out.println("Wcisnales XD");
    }

    @FXML
    private void clickXDD() {
        System.out.println("Wcisnales XDD");
    }

    @FXML
    private void clickXDDD() {
        System.out.println("Wcisnales XDDD");
    }

    @FXML
    private void addDist() {
        model.newDistributor();
    }
}
