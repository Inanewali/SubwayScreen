package ca.ucalgary.edu.ensf380;

public class Advertisement {
    private String title;
    private String mediaPath;

    public Advertisement(String title, String mediaPath) {
        this.title = title;
        this.mediaPath = mediaPath;
    }

    public String getTitle() {
        return title;
    }

    public String getMediaPath() {
        return mediaPath;
    }
}
