import java.util.ArrayList;

class Series extends Product {

    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    Series(String title, String description, int duration, float score, String imgPath) {
        super(title, description, duration, score, imgPath);
    }
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
