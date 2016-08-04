package com.softjourn.sj_coin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsMachineViewFragment;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class Navigation implements Constants{

    public static void goToVendingActivity(Context context){
        Intent intent = new Intent(context,VendingActivity.class);
        context.startActivity(intent);
    }

    public static void goToLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void goToMachineView(Activity activity){
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag(TAG_PRODUCTS_MACHINE_FRAGMENT);
        if (fragment == null) {
            fragment = ProductsMachineViewFragment.newInstance();
        }
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list, fragment, TAG_PRODUCTS_MACHINE_FRAGMENT)
                .commit();
    }

    public static void goToListView(Activity activity){
        android.app.Fragment fragment = activity.getFragmentManager().findFragmentByTag(TAG_PRODUCTS_LIST_FRAGMENT);
        if (fragment == null) {
            fragment = ProductsListFragment.newInstance();
        }
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list, fragment, TAG_PRODUCTS_LIST_FRAGMENT)
                .commit();
    }
}
