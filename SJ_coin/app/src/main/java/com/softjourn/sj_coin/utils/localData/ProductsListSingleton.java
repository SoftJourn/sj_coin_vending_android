package com.softjourn.sj_coin.utils.localData;


import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public class ProductsListSingleton {

    private static ProductsListSingleton sOurInstance = new ProductsListSingleton();

    private List<Product> mProductsList;

    private ProductsListSingleton() {
    }

    public static ProductsListSingleton getInstance() {
        return sOurInstance;
    }

    public void setData(List<Product> products){
        this.mProductsList = products;
    }

    public List<Product> getData(){
        return mProductsList;
    }
}
