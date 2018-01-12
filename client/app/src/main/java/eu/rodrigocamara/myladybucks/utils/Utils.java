package eu.rodrigocamara.myladybucks.utils;

import java.util.List;

import eu.rodrigocamara.myladybucks.pojos.Coffee;

/**
 * Created by Rodrigo Câmara on 12/01/2018.
 */

public class Utils {
    public static int getFinalOrderValue() {
        int price = 0;
        for (Coffee coffee : OrderHelper.getInstance().getOrderList()) {
            price = price + (coffee.getQuantity() * (coffee.getPrice()));
        }
        return price;
    }

    public static int getFinalOrderValue(List<Coffee> coffees) {
        int price = 0;
        for (Coffee coffee : coffees) {
            price = price + (coffee.getQuantity() * (coffee.getPrice()));
        }
        return price;
    }
}
