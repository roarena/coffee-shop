package eu.rodrigocamara.myladybucks.pojos;

import android.support.annotation.Keep;

import org.parceler.Parcel;

import java.util.Currency;
import java.util.Locale;

import eu.rodrigocamara.myladybucks.utils.C;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */
@Parcel @Keep
public class Coffee {
    public String name;
    public String description;
    public String imageURL;
    public int price;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int quantity;

    public Coffee() {

    }

    public Coffee(String name, String description, String imageURL, int price) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPrice() {
        return price;
    }

    public String printPrice() {
        return Currency.getInstance(Locale.getDefault()).getSymbol() + getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

}
