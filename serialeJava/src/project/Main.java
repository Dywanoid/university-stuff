package project;

import project.entities.Simulation;
import project.window.Controller;

import java.io.Serializable;

public class Main implements Serializable {
    static Controller controller = null;
    static Simulation simulation = null;

    public static void main(String[] args) {
        new Simulation();
    }
}
