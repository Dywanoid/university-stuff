package project.entities;

public class Distributor {
    private static int numberOfDistributors = 0;
    private String name;
    private int ID;

    public Distributor(String name) {
        this.name = name;
        this.ID = numberOfDistributors++;
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                '}';
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
