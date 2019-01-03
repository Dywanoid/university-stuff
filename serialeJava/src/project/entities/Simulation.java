package project.entities;

import project.window.WindowApp;
import javafx.application.Application;

public class Simulation  implements Runnable {
    public static VOD vod;
    private static volatile int simulationTime = 0;
    private boolean alive = true;
    private int timeUnderLine = 0;

    public Simulation() {
        vod = new VOD();
        Thread thread = new Thread(this);
        thread.start();
        System.out.println("Here we go!");
        Application.launch(WindowApp.class);

    }

    public static synchronized int getSimulationTime() {
        return simulationTime;
    }

    private synchronized void increaseTime() {
        simulationTime++;
    }

    @Override
    public void run() {
        while(alive && !vod.closed()) {
            try {
                if(simulationTime % 30 == 0 && simulationTime > 0) {
                    vod.takeSubscriptionMoney();
                    vod.pay();
                }
                System.out.println("Time: " + getSimulationTime());
                increaseTime();
                Thread.sleep(1000);
                if(vod.getMoney() < 0) {
                    timeUnderLine++;
                } else {
                    timeUnderLine = 0;
                }
                if(timeUnderLine > 90) {
                    System.out.println("Simulation stops!");
                    kill();
                    vod.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill() {
        alive = false;
    }

}
