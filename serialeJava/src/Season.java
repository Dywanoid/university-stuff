import java.util.ArrayList;

public class Season {
    private ArrayList<Episode> episodes;
    private int seasonID;

    public Season() {
        this.episodes = new ArrayList<>();
    }

    public Season(ArrayList<Episode> episodes) {
            this.episodes = episodes;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(Episode episode) {
        this.episodes.add(episode);
    }

}
