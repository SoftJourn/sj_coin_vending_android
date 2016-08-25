package com.softjourn.sj_coin;


import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public class ProductsListSingleton {

    private static ProductsListSingleton ourInstance = new ProductsListSingleton();

    private List<Product> sProductsList;

    private ProductsListSingleton() {
    }

    public static ProductsListSingleton getInstance() {
        return ourInstance;
    }

    public void setData(List<Product> products){
        this.sProductsList = products;
    }

    public List<Product> getData(){
        return sProductsList;
    }
}
