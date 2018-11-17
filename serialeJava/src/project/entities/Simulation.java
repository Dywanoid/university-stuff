package project.entities;

import java.util.ArrayList;

public class Simulation {
    private VOD current;
    private ArrayList<VOD> vods = new ArrayList<>();

    public Simulation() {
        VOD vod = new VOD();

        this.current = vod;
        this.vods.add(vod);
    }

    public void start() {
        current.start();
    }
}
