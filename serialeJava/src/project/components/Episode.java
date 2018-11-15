package project.components;

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

    public String getTitle() {
        return title;
    }

    public Date getPremiereDate() {
        return premiereDate;
    }

    public int getDuration() {
        return duration;
    }
}
