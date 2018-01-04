package eu.rodrigocamara.myladybucks.pojos;

import org.parceler.Parcel;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */
@Parcel
public class Coffee {
    private String name;
    private String description;
    private String imageURL;
    private String price;

    public Coffee() {

    }

    public Coffee(String name, String description, String imageURL, String price) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
