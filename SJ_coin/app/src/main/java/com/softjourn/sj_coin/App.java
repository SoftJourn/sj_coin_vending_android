package com.softjourn.sj_coin;

import android.app.Application;
import android.content.Context;

import com.softjourn.sj_coin.utils.Const;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static Context sInstance;

    public static Context getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this.getApplicationContext();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Const.DB_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
