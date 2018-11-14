import java.util.Date;

public class Episode {
    private String title;
    private Date premiereDate;
    private int duration;

    public Episode(String title, Date premiereDate, int duration) {
        this.title = title;
        this.premiereDate = premiereDate;
        this.duration = duration;
    }
}
