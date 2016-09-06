package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.CustomizedProduct;

public class OnProductBuyClickEvent {

        private CustomizedProduct mProduct;

        public OnProductBuyClickEvent(CustomizedProduct product) {
            this.mProduct = product;
        }

        public CustomizedProduct buyProduct(){
            return mProduct;
        }

}
