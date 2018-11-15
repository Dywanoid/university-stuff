package project;

import project.entities.distributor.Distributor;
import project.products.series.Series;

import java.util.Date;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Series test = new Series("path", "tytul", "opis", new Date(), 60, new Distributor(), new ArrayList<>(), 5.2f, 6, new ArrayList<>(), "gatunek");
       System.out.println(test.toString());
    }

}