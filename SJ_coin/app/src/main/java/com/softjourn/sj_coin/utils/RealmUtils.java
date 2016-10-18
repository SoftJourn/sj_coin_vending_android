package com.softjourn.sj_coin.utils;

import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Featured;

import java.util.List;

import io.realm.Realm;

public class RealmUtils {

    public static void setRealmData(Realm realm, Featured products) {
        realm.beginTransaction();
        realm.copyToRealm(products);
        realm.commitTransaction();
    }

    public static void setRealmData(Realm realm, List<Favorites> favorites) {
        realm.beginTransaction();
        realm.copyToRealm(favorites);
        realm.commitTransaction();
    }

    public static void setRealmHistoryData(Realm realm, List<History> history) {
        realm.beginTransaction();
        realm.copyToRealm(history);
        realm.commitTransaction();
    }

    public static void clearAllDB(Realm realm) {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

}
