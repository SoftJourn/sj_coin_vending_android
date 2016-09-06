package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.AllProducts;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastAddedFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;


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

    public static void goToAllProductsActivity(Context context){
        Intent intent = new Intent(context, AllProducts.class);
        context.startActivity(intent);
    }

    public static void goToProductListFragments(Activity activity) {
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_new_products, ProductsListLastAddedFragment.newInstance(), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                .replace(R.id.container_fragment_products_list_last_purchase, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_snacks, ProductListSnacksFragment.newInstance(),TAG_PRODUCTS_SNACKS_FRAGMENT)
                .replace(R.id.container_fragment_products_list_drinks, ProductListDrinksFragment.newInstance(),TAG_PRODUCTS_DRINKS_FRAGMENT)
                .commit();
    }

    public static void navigationOnCategoriesAllProducts(int position, Activity context) {
        switch (position) {
            case 1:
                Navigation.goToSeeAllActivity(context, LAST_ADDED);
                context.finish();
                break;
            case 2:
                Navigation.goToSeeAllActivity(context, LAST_PURCHASES);
                context.finish();
                break;
            case 3:
                Navigation.goToSeeAllActivity(context, BEST_SELLERS);
                context.finish();
                break;
            case 4:
                Navigation.goToSeeAllActivity(context, SNACKS);
                context.finish();
                break;
            case 5:
                Navigation.goToSeeAllActivity(context, DRINKS);
                context.finish();
                break;
        }
    }

    public static void navigationOnCategoriesSeeAll(int position, Activity context){
        switch (position){
            case 0:
                Navigation.goToAllProductsActivity(context);
                context.finish();
                break;
            case 1:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastAddedFragment.newInstance(), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            case 2:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                        .commit();
                break;
            case 3:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case 4:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListSnacksFragment.newInstance(), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case 5:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListDrinksFragment.newInstance(), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
        }
    }
}
