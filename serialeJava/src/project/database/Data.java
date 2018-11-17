package project.database;

import project.components.Subscription;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Data {
    private static Map<String, Subscription> subsciptions = Map.of(
            "Basic", new Subscription(29.99f, 1, "HD"),
            "Family", new Subscription(39.99f, 2, "FullHD"),
            "Premium", new Subscription(49.99f, 4, "UltraHD"));

    private final static String PROJECT_PATH = "./src/project";
    private final static String TXT_PATH = "/database/txt/";
    private ArrayList<String> files = new ArrayList<>();
    private Map<String, Integer> linesCount = new HashMap<>();
    private Map<String, ArrayList<String>> data = new HashMap<>();
    private Random generator = new Random();

    public Data() {
        getFilesNames();
        readFiles();
    }

    private void getFilesNames() {
        try {
            Scanner fileIn = new Scanner(new File(PROJECT_PATH + TXT_PATH + "files.txt"));
            while(fileIn.hasNextLine()) {
                String fileName = fileIn.nextLine();
                files.add(fileName);
                linesCount.put(fileName, 0);
                data.put(fileName, new ArrayList<>());
            }
            fileIn.close();
        }
        catch (FileNotFoundException i){i.printStackTrace();}
    }

    private void readFiles() {
        for (String name: files) {
            try {
                Scanner fileIn = new Scanner(new File(PROJECT_PATH + TXT_PATH + name + ".txt"));
                int count = 0;
                ArrayList<String> entries = new ArrayList<>();
                while(fileIn.hasNextLine()) {
                    count++;
                    entries.add(fileIn.nextLine());
                }
                linesCount.put(name, count);
                data.put(name, entries);
                fileIn.close();
            }
            catch(FileNotFoundException i) {i.printStackTrace();}
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "\nfiles=" + files +
                ", \nlinesCount=" + linesCount +
                ", \ndata=" + data +'}';
    }

    public String getRandomText(String name) {
        int lines = linesCount.get(name);
        int randomLine = generator.nextInt(lines - 1);

        return data.get(name).get(randomLine);
    }

    public int getRandomInt(int from, int to) { // 5, 10
        int result = generator.nextInt(to - from);
        return result + from;
    }

    public float getRandomFloat(float from, float to) {
        double result = generator.nextDouble();
    //TODO: Finish this
        return 2.1f;
    }
}

// SERIALIZACJA

//        Subscription test2 = new Subscription(25.2f, 2, "HD");
//        try {
//            FileOutputStream fileOut =
//                    new FileOutputStream("C:\\Users\\X\\Desktop\\subscription.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(test2);
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in C:\\Users\\X\\Desktop\\subscription.ser");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }

//        Subscription el;
//        try {
//            FileInputStream fileIn = new FileInputStream("C:\\Users\\X\\Desktop\\subscription.ser");
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            el = (Subscription) in.readObject();
//            in.close();
//            fileIn.close();
//            System.out.println(el.toString());
//        } catch (IOException i) {
//            i.printStackTrace();
//        } catch (ClassNotFoundException c) {
//            System.out.println("Employee class not found");
//            c.printStackTrace();
//        }