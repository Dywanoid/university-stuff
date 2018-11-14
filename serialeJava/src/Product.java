import java.util.Date;

abstract class Product {
    private String title;
    private String description;
    private int duration;
    private float score;
    private String imgPath;
    private Distributor distributor;
    private Date date;

    Product(String title, String description, int duration, float score, String imgPath) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.score = score;
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", score=" + score +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
