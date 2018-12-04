package project.entities;

public class Simulation {
    public VOD vod;
    private int simulationTime = 0;

    public Simulation() {

        this.vod = new VOD();
    }

    public void start() {
        vod.start();
    }
}
