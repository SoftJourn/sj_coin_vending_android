package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListFeaturedFragment;
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

    public static void goToProductListFragments(Activity activity) {
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_new_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_last_purchase, ProductsListFeaturedFragment.newInstance(), TAG_PRODUCTS_FEATURED_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                .commit();
    }

}
