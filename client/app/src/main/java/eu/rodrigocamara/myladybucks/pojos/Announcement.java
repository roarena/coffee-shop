package eu.rodrigocamara.myladybucks.pojos;

/**
 * Created by rodri on 30/12/2017.
 */

public class Announcement {
    private String url;
    private String imageUrl;

    public Announcement(){

    }
    public Announcement(String url, String imageUrl) {
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
