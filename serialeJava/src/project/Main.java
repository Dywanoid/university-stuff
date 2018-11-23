package project;

import project.entities.Distributor;
import project.products.Series;
import project.database.Data;
import project.entities.Simulation;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.start();
        String testujemy = "XDDDD";
        try {
            System.out.println(Utilities.hashFunction(testujemy));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
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

//        Data d = new Data();
//        for (int i = 0; i < 15; i++) {
//            System.out.println(d.getRandomText("names1")+" "+d.getRandomText("names2"));
//            System.out.println(d.getRandomText("email1")+"@"+d.getRandomText("email2"));
//            System.out.println(d.getRandomInt(10, 5));
//            System.out.println(d.getRandomFloat(5.23f, 6.11f));
//        }


    }

}