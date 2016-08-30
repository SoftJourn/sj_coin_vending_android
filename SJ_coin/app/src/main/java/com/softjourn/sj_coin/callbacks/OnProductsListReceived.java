package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public class OnProductsListReceived {

    private List<Product> mProduct;

    public OnProductsListReceived(List<Product> product) {
        this.mProduct = product;
    }

    public List<Product> getProductsList(){
        return mProduct;
    }
}
