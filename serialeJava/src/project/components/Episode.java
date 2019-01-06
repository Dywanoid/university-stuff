package project.components;

import java.io.Serializable;

public class Episode implements Serializable {
    private String title;
    private String premiereDate;
    private int duration;

    public Episode(String title, String premiereDate, int duration) {
        this.title = title;
        this.premiereDate = premiereDate;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", premiereDate='" + premiereDate + '\'' +
                ", duration=" + duration +
                '}';
    }

    public int getDuration() {
        return duration;
    }
}
