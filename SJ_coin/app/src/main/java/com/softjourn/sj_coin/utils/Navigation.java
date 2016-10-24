package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.NoInternetActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.SeeAllActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;

public class Navigation implements Const,Extras {

    public static void goToNoInternetScreen(Context context){
        Intent intent = new Intent(context, NoInternetActivity.class);
        context.startActivity(intent);
    }

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

    public static void navigationOnCategoriesSeeAll(int position, Activity context, @Nullable String category){
        switch (position){
            case 0:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_ITEMS,0,0), TAG_ALL_PRODUCTS_FRAGMENT)
                        .commit();
                break;
            case 1:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES,0,0), TAG_FAVORITES_FRAGMENT)
                        .commit();
                break;
            case 2:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(LAST_ADDED,0,0), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            case 3:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(BEST_SELLERS,0,0), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            default:
                context.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(category,0,0), category)
                        .commit();
        }
    }
}
