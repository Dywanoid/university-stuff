package project.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.entities.Simulation;

import java.io.Serializable;

public class WindowApp extends Application implements Serializable {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        Simulation.vod.init();
        controller.setModel(Simulation.vod);
        controller.setStage(stage);
        controller.controlPanel();

        stage.setTitle("Seriale");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}
