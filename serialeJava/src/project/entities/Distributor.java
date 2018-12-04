package project.entities;

import java.util.ArrayList;
import java.util.Random;
import project.utils.Utilities;
import project.database.VODdata;
import project.products.*;
import project.components.Episode;
import project.components.Season;

public class Distributor {
    private static int numberOfDistributors = 0;
    private String name;
    private int ID;
    private ArrayList<Product> products = new ArrayList<>();

    Distributor(String name) {
        this.name = name;
        this.ID = numberOfDistributors++;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", products=" + products +
                '}';
    }

    public void newProduct(VODdata data) {
        Product product = null;
        int wynik = (int) ((new Random()).nextFloat() * 3);
        System.out.println(wynik);
        switch (wynik) {
            case 0:
                product = new Series();
                generateSeries(data, (Series) product);
                break;
            case 1:
                product = new Movie();
                generateMovie(data, (Movie) product);
                break;
            case 2:
                product = new Stream();
                generateStream(data, (Stream) product);
                break;
        }
        products.add(product);
    }

    private void generateProduct(VODdata data, Product product) {
        product.setImgPath("empty");
        product.setTitle(data.getRandomText("title"));
        product.setDescription(data.getRandomText("description"));
        product.setProductionDate(Utilities.getRandomDate(365 * -40, 10));
        product.setDuration(Utilities.getRandomInt(60, 120));
        product.setDistributor(this);
        product.setCountries(data.getRandomText("country", -1));
        product.setScore(Utilities.getRandomFloat(1, 10));
    }

    private void generateSeries(VODdata data, Series product) {
        int numberOfSeasons = Utilities.getRandomInt(1, 5);
        int numberOfEpisodes = Utilities.getRandomInt(numberOfSeasons * 3, numberOfSeasons * 6);
        ArrayList<Season> seasons = new ArrayList<>();
        for (int s = 0; s < numberOfSeasons; s++) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (int e = 0; e < numberOfEpisodes; e++) {
                episodes.add(new Episode("NAME","DATE", Utilities.getRandomInt(30, 70))); // TODO: CHANGE THOSE STRINGS
            }
            seasons.add(new Season(episodes, s + 1));
        }

        generateProduct(data, product);

        product.setGenre(data.getRandomText("genre"));
        product.setActors(data.getRandomText("actor", 5));
        product.setNumberOfSeasons(numberOfSeasons);
        product.setNumberOfEpisodes(numberOfEpisodes);
        product.setSeasons(seasons);
        product.calculateDuration();
    }

    private void generateMovie(VODdata data, Movie product) {
        generateProduct(data, product);
        product.setGenre(data.getRandomText("genre"));
        product.setActors(data.getRandomText("actor", 5));
        product.setTrailerURL("URL"); // TODO: CHANGE THIS
        product.setAvalibleToWatchTime(Utilities.getRandomInt(30, 90));
        product.setSale(null); // TODO: sales?
    }

    private void generateStream(VODdata data, Stream product) {
        generateProduct(data, product);
        product.setDate(Utilities.getRandomInt(15, 100));
        product.setSale(null); // TODO: sales?
    }

    public static int getNumberOfDistributors() {
        return numberOfDistributors;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}