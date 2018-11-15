package project;

import project.entities.distributor.Distributor;
import project.products.Series;

import java.util.Date;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Series test = new Series("path",
                "tytul",
                "opis",
                new Date(),
                60,
                new Distributor("Sony"),
                new ArrayList<>(),
                5.2f,
                "gatunek",
                15.23f,
                new ArrayList<>(),
                1,
                new ArrayList<>(),
                5);
        System.out.println(test.toString());

    }

}