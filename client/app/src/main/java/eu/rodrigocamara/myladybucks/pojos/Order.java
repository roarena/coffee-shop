package eu.rodrigocamara.myladybucks.pojos;

import org.parceler.Parcel;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */
@Parcel
public class Order {
    Coffee coffee;
    int quantity;

    public Order() {

    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
