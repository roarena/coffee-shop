package eu.rodrigocamara.myladybucks.pojos;

/**
 * Created by rodri on 30/12/2017.
 */

public class Announcement {
    private String url;
    private int imageUrl;

    public Announcement(){

    }
    public Announcement(String url, int imageUrl) {
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
