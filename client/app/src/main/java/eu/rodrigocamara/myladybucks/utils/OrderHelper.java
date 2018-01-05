package eu.rodrigocamara.myladybucks.utils;

import java.util.ArrayList;
import java.util.List;

import eu.rodrigocamara.myladybucks.pojos.Order;

/**
 * Created by Rodrigo Câmara on 04/01/2018.
 */

public class OrderHelper {
    private static OrderHelper instance = null;
    static List<Order> orderList;

    protected OrderHelper() {
        // Exists only to defeat instantiation.
    }

    public static OrderHelper getInstance() {
        if (instance == null) {
            instance = new OrderHelper();
            orderList = new ArrayList<>();
        }
        return instance;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
