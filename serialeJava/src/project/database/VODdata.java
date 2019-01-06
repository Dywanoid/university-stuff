package project.database;

import javafx.scene.image.Image;
import project.utils.Utilities;
import project.components.Subscription;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VODdata implements Serializable {
    private static Map<String, Subscription> subscriptions = new HashMap<>();

    private ArrayList<String> files = new ArrayList<>();
    private Map<String, Integer> linesCount = new HashMap<>();
    private Map<String, ArrayList<String>> data = new HashMap<>();
    private ArrayList<Image> images = new ArrayList<>();


    public VODdata() {
        subscriptions.put("Basic", new Subscription(29.99f, 1, "HD"));
        subscriptions.put("Family", new Subscription(39.99f, 2, "FullHD"));
        subscriptions.put("Premium", new Subscription(49.99f, 4, "UltraHD"));
        getFilesNames();
        readFiles();
        getImages();
    }

    public static Map<String, Subscription> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Change Subscription price
     * @param key name of Subscription
     * @param price new price
     */
    public static void changeSubscriptionPrice(String key, Float price) {
        subscriptions.get(key).setPrice(price);
    }

    private void getFilesNames() {
        try {
            InputStream is = getClass().getResourceAsStream("txt/files.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String fileName;
            while((fileName = reader.readLine()) != null) {
                files.add(fileName);
                linesCount.put(fileName, 0);
                data.put(fileName, new ArrayList<>());
            }
            is.close();
            reader.close();

        } catch (Exception i) {
            i.printStackTrace();
        }
    }

    private void readFiles() {
        for (String name : files) {
            try {
                InputStream is = getClass().getResourceAsStream(String.format("txt/%s.txt", name));
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String entry;
                ArrayList<String> entries = new ArrayList<>();

                int count = 0;
                while((entry = reader.readLine()) != null) {
                    count++;
                    entries.add(entry);
                }
                linesCount.put(name, count);
                data.put(name, entries);
                is.close();
                reader.close();
            } catch (Exception i) {
                i.printStackTrace();
            }
        }
    }

    private void getImages() {
        try {
            InputStream is = getClass().getResourceAsStream("txt/img.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String imgName;
            while((imgName = reader.readLine()) != null) {
                images.add(new Image(getClass().getResourceAsStream("img/" + imgName)));
            }
            is.close();
            reader.close();

        } catch (Exception i) {
            i.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "VODdata{" +
                "\nfiles=" + files +
                ", \nlinesCount=" + linesCount +
                ", \ndata=" + data + '}';
    }

    /**
     * Get random text from database
     * @param name name of text(i.e. "genre", "email1", etc)
     * @return random text
     */
    public String getRandomText(String name) {
        int randomLine = Utilities.getRandomInt(0, linesCount.get(name) - 1);
        return data.get(name).get(randomLine);
    }

    /**
     * Get random text/texts from database
     * @param name name of text(i.e. "genre", "email1", etc)
     * @param numberOfTexts how many texts to return, will be random from 0 to size of texts ArrayList if less than 0
     * @return ArrayList containing texts
     */
    public ArrayList<String> getRandomText(String name, int numberOfTexts) {
        ArrayList<String> texts = new ArrayList<>();
        final int nTexts = numberOfTexts <= 0 ? Utilities.getRandomInt(1, linesCount.get(name)) : numberOfTexts;
        for (int i = 0; i < nTexts; i++) {
            String randomText = data.get(name).get(Utilities.getRandomInt(0, linesCount.get(name) - 1));
            if(!texts.contains(randomText)) {
                texts.add(randomText);
            }
        }
        return texts;
    }

    /**
     * Get random image from database
     * @return random image
     */
    public Image getRandomImage() {
        return images.get(Utilities.getRandomInt(0, images.size() - 1));
    }

    /**
     * Get random Subscription
     * @return random Subscription
     */
    public Subscription getRandomSubscription() {
        int rand = (int) (Math.random() * 4);
        return  rand == 3 ? null : (Subscription) subscriptions.values().toArray()[rand];
    }
}