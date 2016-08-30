package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.AllProducts;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.ProductActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListNewProductsFragment;
import com.softjourn.sj_coin.model.products.Product;


public class Navigation implements Constants,Extras {

    public static void goToVendingActivity(Context context) {
        Intent intent = new Intent(context, VendingActivity.class);
        context.startActivity(intent);
    }

    public static void goToLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void goToProfileActivity(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        context.startActivity(intent);
    }

    public static void goToSeeAllActivity(Context context, String category){
        Intent intent = new Intent(context, SeeAllActivity.class);
        intent.putExtra(EXTRAS_CATEGORY,category);
        context.startActivity(intent);
    }

    public static void goToProductActivity(Context context, Product product){
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRAS_CONCRETE_PRODUCT, product);
        context.startActivity(intent);
    }

    public static void goToAllProductsActivity(Context context){
        Intent intent = new Intent(context, AllProducts.class);
        context.startActivity(intent);
    }

    public static void goToProductListFragments(Activity activity) {
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_new_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_last_purchase, ProductsListNewProductsFragment.newInstance(), TAG_PRODUCTS_NEW_PRODUCT_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_snacks, ProductListSnacksFragment.newInstance(),TAG_PRODUCTS_SNACKS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_drinks, ProductListDrinksFragment.newInstance(),TAG_PRODUCTS_DRINKS_FRAGMENT)
                .commit();
    }

}
