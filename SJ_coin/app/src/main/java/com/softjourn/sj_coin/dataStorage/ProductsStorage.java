package com.softjourn.sj_coin.dataStorage;

import com.softjourn.sj_coin.api_models.products.Product;

import java.util.Collections;
import java.util.List;

/**
 * Created by omartynets on 04.11.2016.
 */
public class ProductsStorage {

    private List<Product> mProductsList;

    private static ProductsStorage sOurInstance = new ProductsStorage();

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
