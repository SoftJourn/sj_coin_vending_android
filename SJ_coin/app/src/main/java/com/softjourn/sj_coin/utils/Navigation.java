package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.ProfileActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductsListOtherFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListSnacksFragment;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class Navigation implements Constants {

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
        context.startActivity(intent);
    }

    public static void goToMachineView(Activity activity) {

        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list, ProductsListOtherFragment.newInstance(), TAG_PRODUCTS_MACHINE_FRAGMENT)
                .commit();
        Preferences.storeObject(SELECTED_VIEW, MACHINE_VIEW);
    }

    public static void goToListView(Activity activity) {

        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list, ProductsListSnacksFragment.newInstance(), TAG_PRODUCTS_LIST_FRAGMENT)
                .commit();
        Preferences.storeObject(SELECTED_VIEW, LIST_VIEW);
    }
}
