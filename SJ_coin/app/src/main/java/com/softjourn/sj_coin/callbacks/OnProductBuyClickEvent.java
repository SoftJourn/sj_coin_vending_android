package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Product;

public class OnProductBuyClickEvent {

        private final Product mProduct;

        public OnProductBuyClickEvent(Product product) {
            this.mProduct = product;
        }

        public Product buyProduct(){
            return mProduct;
        }

}
