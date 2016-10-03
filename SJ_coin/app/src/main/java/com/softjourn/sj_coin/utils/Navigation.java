package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;

public class Navigation implements Const,Extras {

    public static void goToVendingActivity(Context context) {
        Intent intent = new Intent(context, VendingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    public static void navigationOnCategoriesSeeAll(int position, Activity context){
        switch (position){
            case 0:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_ITEMS), TAG_ALL_PRODUCTS_FRAGMENT)
                        .commit();
                break;
            case 1:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES), TAG_FAVORITES_FRAGMENT)
                        .commit();
                break;
            case 2:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(LAST_ADDED), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            case 3:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(BEST_SELLERS), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case 4:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(SNACKS), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case 5:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(DRINKS), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
        }
    }
}
