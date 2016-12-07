package com.softjourn.sj_coin.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.VendingActivity;
import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.models.Amount;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.events.OnAmountReceivedEvent;
import com.softjourn.sj_coin.events.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.ServerErrors;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by omartynets on 07.12.2016.
 */

public class PurchaseService extends IntentService implements Extras, Const {

    private final EventBus mEventBus = EventBus.getDefault();

    private int NOTIFICATION_ID = 1000001;

    public PurchaseService() {
        super(PurchaseService.class.getName());

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String mProductId = intent.getStringExtra(EXTRAS_PRODUCT_ID);
        String mMachineID = intent.getStringExtra(EXTRAS_MACHINE_ID);

        showNotification(getString(R.string.order_processing_message));

        buyProductByID(mMachineID, mProductId);

        this.stopSelf();
    }

    private void buyProductByID(String machineID, String id) {

        final VendingApiProvider mApiProvider = ApiManager.getInstance().getVendingProcessApiProvider();
        mApiProvider.buyProductByID(machineID, id, new com.softjourn.sj_coin.api.callbacks.Callback<Amount>() {

            @Override
            public void onSuccess(Amount response) {
                mEventBus.post(new OnAmountReceivedEvent(response));
                showNotification(getString(R.string.activity_product_take_your_order_message));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
                showNotification(ServerErrors.showErrorMessage(errorMsg));
            }
        });
    }

    private void showNotification(String message) {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("SJ Coins Purchase")
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(App.getContext(), R.color.colorAccent))
                .setContentText(message);

        if (!message.equals(getString(R.string.order_processing_message))) {
            builder.setVibrate(new long[]{0, 500});
            builder.setPriority(Notification.PRIORITY_MAX);
            builder.setSmallIcon(R.drawable.ic_basket_white_24dp);
        } else {
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            builder.setSmallIcon(R.drawable.basket_animation);
            builder.setVibrate(new long[]{0, 0});
            builder.setProgress(0, 0, true);
        }

        Intent notificationIntent = new Intent(this, VendingActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(notificationIntent), 0);

        builder.setContentIntent(pendingIntent);

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
