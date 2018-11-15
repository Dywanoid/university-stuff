package project.components;

import java.util.Map;

public abstract class Data {
    private static Map<String, Subscription> subsciptions = Map.of(
            "Basic", new Subscription(29.99f, 1, "HD"),
            "Family", new Subscription(39.99f, 2, "FullHD"),
            "Premium", new Subscription(49.99f, 4, "UltraHD"));
}

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