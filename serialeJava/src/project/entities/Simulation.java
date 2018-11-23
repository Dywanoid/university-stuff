package project.entities;

public class Simulation {
    private VOD vod;

    public Simulation() {
        this.vod = new VOD();
    }

    public void start() {
        vod.start();
    }
}
