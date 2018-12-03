package project.components;

public class Episode {
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

    public String getTitle() {
        return title;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public int getDuration() {
        return duration;
    }
}
