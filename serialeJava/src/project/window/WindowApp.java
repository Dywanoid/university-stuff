package project.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.entities.Simulation;
import project.entities.VOD;

public class WindowApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
        Parent root = loader.load();

        Controller contr = loader.getController();
        contr.setModel(Simulation.vod);

        stage.setTitle("Seriale");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
