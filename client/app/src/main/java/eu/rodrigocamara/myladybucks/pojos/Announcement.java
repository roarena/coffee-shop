package eu.rodrigocamara.myladybucks.pojos;

import android.support.annotation.Keep;

/**
 * Created by rodri on 30/12/2017.
 */
@Keep
public class Announcement {
    public String url;
    public String imageUrl;

    public Announcement() {

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
