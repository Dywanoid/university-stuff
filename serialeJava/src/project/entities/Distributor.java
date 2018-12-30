package project.entities;

import java.util.ArrayList;
import java.util.Random;
import project.utils.Utilities;
import project.database.VODdata;
import project.products.*;
import project.components.Episode;
import project.components.Season;

public class Distributor implements Runnable{
    private boolean alive = true;
    private static int numberOfDistributors = 0;
    private String name;
    private int ID;
    private ArrayList<Product> products = new ArrayList<>();
    private VOD VODpointer;
    private final Integer productMonitor = 0;

    Distributor(String name, VOD pointer) {
        this.name = name;
        this.ID = numberOfDistributors++;
        this.VODpointer = pointer;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", products=" + products +
                '}';
    }

    void newRandomProduct(VODdata data) {
        int result = (int) ((new Random()).nextFloat() * 3);
        switch (result) {
            case 0:
                newSeries(data);
                break;
            case 1:
                newMovie(data);
                break;
            case 2:
                newStream(data);
                break;
        }
    }

    private void generateProduct(VODdata data, Product product) {
        product.setImage(data.getRandomImage());
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
        product.setTrailerURL("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        product.setAvalibleToWatchTime(Utilities.getRandomInt(30, 90));
        product.setSale(null); // TODO: sales?

    }

    private void generateStream(VODdata data, Stream product) {
        generateProduct(data, product);
        product.setDate(Utilities.getRandomInt(15, 100));
        product.setSale(null); // TODO: sales?

    }

    void newSeries(VODdata data) {
        Series product = new Series();
        generateProduct(data, product);
        generateSeries(data,  product);
        synchronized (productMonitor) {
            VODpointer.seriesAdded();
            products.add(product);
            VODpointer.addProduct(product);
        }
    }

    void newMovie(VODdata data) {
        Movie product = new Movie();
        generateProduct(data, product);
        generateMovie(data, product);
        synchronized (productMonitor) {
            VODpointer.movieAdded();
            products.add(product);
        }
            VODpointer.addProduct(product);

    }

    void newStream(VODdata data) {
        Stream product = new Stream();
        generateProduct(data, product);
        generateStream(data, product);
        synchronized (productMonitor) {
            VODpointer.streamAdded();
            products.add(product);
            VODpointer.addProduct(product);
        }
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

    public void deleteProductFromMe(Product product) {
        products.remove(product);
    }

    public void deleteProductFromVOD(Product product) {
        VODpointer.deleteProduct(product);
    }

    @Override
    public void run() {
        while(alive) {
            long sleepTime = (long) (1000 * Math.random() * 4);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(alive) {
                if(Math.random() * 100 > 60) {
//                    newRandomProduct(VODpointer.data);
                }

//                System.out.println(name + " " + sleepTime);
            }

        }
    }

    void kill() {
        alive = false;
    }

}
