import java.util.ArrayList;
import java.util.Date;

public class Stream extends Product{
    private Date date;
    private double price;

    public Stream(String imgPath, String title, String description, Date productionDate, int duration, Distributor distributor, ArrayList<String> countries, float score, Date date, double price) {
        super(imgPath, title, description, productionDate, duration, distributor, countries, score);
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
