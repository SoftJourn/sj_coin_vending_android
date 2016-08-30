package com.softjourn.sj_coin.activities;

import android.os.Bundle;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListNewProductsFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

public class SeeAllActivity extends BaseActivity implements Constants, Extras {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        setTitle(getIntent().getStringExtra(EXTRAS_CATEGORY));

        switch (getIntent().getStringExtra(EXTRAS_CATEGORY)){
            case SNACKS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListSnacksFragment.newInstance(), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case DRINKS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListDrinksFragment.newInstance(), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case NEW_PRODUCTS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListNewProductsFragment.newInstance(), TAG_PRODUCTS_NEW_PRODUCT_FRAGMENT)
                        .commit();
                break;
            case LAST_PURCHASES:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                        .commit();
                break;
        }
    }
}
