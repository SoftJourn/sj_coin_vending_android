package com.softjourn.sj_coin.dataStorage;

import com.softjourn.sj_coin.api_models.products.Featured;
import com.softjourn.sj_coin.api_models.products.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omartynets on 04.11.2016.
 */
public class FeaturesStorage {

    private Featured mFeaturesProducts;

    private static FeaturesStorage sOurInstance = new FeaturesStorage();

    public static FeaturesStorage getInstance() {
        return sOurInstance;
    }

    private FeaturesStorage() {
    }

    public void setData(Featured products) {
        this.mFeaturesProducts = products;

        ProductsStorage.getInstance().onDestroy();
        setProductStorage();
    }

    public Featured getData() {
        return mFeaturesProducts == null ? new Featured() : mFeaturesProducts;
    }

    public void onDestroy() {
        if (mFeaturesProducts != null) {
            mFeaturesProducts = null;
        }
    }

    /**
     * Adding products to Local Product Storage.
     * by retrieving objects from Category->List<Product>
     */
    private void setProductStorage() {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < mFeaturesProducts.getCategories().size(); i++) {
            for (int j = 0; j < mFeaturesProducts.getCategories().get(i).getProducts().size(); j++) {
                productList.add(mFeaturesProducts.getCategories().get(i).getProducts().get(j));
            }
        }

        ProductsStorage.getInstance().setData(productList);
    }
}
