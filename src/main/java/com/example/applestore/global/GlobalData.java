package com.example.applestore.global;

import com.example.applestore.model.Product;
import java.util.ArrayList;
import java.util.List;


public class GlobalData {
    //tao bien toan cuc
    public static List<Product> cart;

    static {
        cart = new ArrayList<>();
    }

}
