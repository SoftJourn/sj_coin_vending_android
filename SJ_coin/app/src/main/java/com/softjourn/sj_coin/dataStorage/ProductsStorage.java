package com.softjourn.sj_coin.datastorage;

import com.softjourn.sj_coin.api.models.products.Product;

import java.util.Collections;
import java.util.List;

/**
 * Created by omartynets on 04.11.2016.
 * Storage of all products grabbed from server
 */
public class ProductsStorage {

    private List<Product> mProductsList;

    private static final ProductsStorage sOurInstance = new ProductsStorage();

    public static ProductsStorage getInstance() {
        return sOurInstance;
    }

    private ProductsStorage() {
    }

    public void setData(List<Product> products){
        this.mProductsList = products;
    }

    public List<Product> getData(){
        return mProductsList == null ? Collections.<Product>emptyList() : mProductsList;
    }

    public void onDestroy() {
        if (mProductsList!=null) {
            mProductsList.clear();
        }
    }
}
