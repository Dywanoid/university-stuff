package project;

import javafx.application.Application;
import project.entities.Simulation;
import project.utils.Utilities;
import project.utils.MyDate;
import project.window.WindowApp;
public class Main {

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.start();
        sim.vod.newDistributor();
        sim.vod.newProducts();
        sim.vod.showDistributors();

        Application.launch(WindowApp.class);

    }

}

//        System.out.println(Utilities.hashFunction("XD"));

//        Series test = new Series("path",
//                "tytul",
//                "opis",
//                new Date(),
//                60,
//                new Distributor("Sony"),
//                new ArrayList<>(),
//                5.2f,
//                "gatunek",
//                15.23f,
//                new ArrayList<>(),
//                1,
//                new ArrayList<>(),
//                5);

//        VODdata d = new VODdata();
//        for (int i = 0; i < 15; i++) {
//            System.out.println(d.getRandomText("names1")+" "+d.getRandomText("names2"));
//            System.out.println(d.getRandomText("email1")+"@"+d.getRandomText("email2"));
//            System.out.println(d.getRandomInt(10, 5));
//            System.out.println(d.getRandomFloat(5.23f, 6.11f));
//        }