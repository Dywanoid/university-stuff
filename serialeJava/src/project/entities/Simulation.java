package project.entities;

import project.window.WindowApp;
import javafx.application.Application;

public class Simulation {
    public static VOD vod;
    public WindowApp app;
    private int simulationTime = 0;

    public Simulation() {
        vod = new VOD();
        Application.launch(WindowApp.class);
    }
}
