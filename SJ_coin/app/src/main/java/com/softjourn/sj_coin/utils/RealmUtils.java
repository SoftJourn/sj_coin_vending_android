package com.softjourn.sj_coin.utils;

import com.softjourn.sj_coin.model.products.Featured;

import io.realm.Realm;

/**
 * Created by omartynets on 05.10.2016.
 */

public class RealmUtils {

    public static void setRealmData(Realm realm, Featured products) {
        realm.beginTransaction();
        realm.copyToRealm(products);
        realm.commitTransaction();
    }

}
