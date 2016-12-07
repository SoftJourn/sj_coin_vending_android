package com.softjourn.sj_coin.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.PurchaseHistoryItemsAdapter;
import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.presenters.ProfilePresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class ProfileActivity extends BaseActivity implements ProfileContract.View, Const {

    @BindView(R.id.profile_coins_label)
    TextView mCoinsLabel;

    @BindView(R.id.profile_user_name)
    TextView mUserName;

    @BindView(R.id.profile_amount_available)
    TextView mUserBalance;

    @BindView(R.id.list_items_recycler_view)
    RecyclerView mHistoryList;

    @BindView(R.id.textViewNoPurchases)
    TextView mNoPurchasesTextView;

    private ProfileContract.Presenter mPresenter;
    private PurchaseHistoryItemsAdapter mHistoryAdapter;

    private final static int PERMISSION_ALL = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        super.mProfileIsVisible = true;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mUserName.setText(Preferences.retrieveStringObject(USER_NAME_PREFERENCES_KEY));

        mPresenter = new ProfilePresenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mHistoryAdapter = new PurchaseHistoryItemsAdapter();
        mHistoryList.setLayoutManager(layoutManager);
        mHistoryList.setAdapter(mHistoryAdapter);

        mPresenter.getAccount();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.mProfileIsVisible = false;
    }

    @Override
    protected void onDestroy() {
        super.mProfileIsVisible = false;
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.select_machine).setVisible(false);
        menu.findItem(R.id.scan_barcode).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.scan_barcode:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                /*In case other permissions needed just add them to String array
                * and work on them in onRequestPermissionsResult*/
                    String[] PERMISSIONS = {Manifest.permission.CAMERA};

                    if (!hasPermissions(this, PERMISSIONS)) {
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                    } else {
                        IntentIntegrator integrator = new IntentIntegrator(this);
                        integrator.initiateScan();
                    }
                } else {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.initiateScan();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.initiateScan();
                } else {
                    Utils.showSnackBar(findViewById(R.id.root_layout_profile), getString(R.string.permissions_camera_message));
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Utils.showSnackBar(findViewById(R.id.root_layout_profile), getString(R.string.permissions_camera_message));
            } else {
                mPresenter.addMoney(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void showBalance(String amount) {
        mCoinsLabel.setVisibility(View.VISIBLE);
        mUserBalance.setText(amount);
        mUserBalance.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    @Override
    public void setUserName(String message) {
        mUserName.setText(message);
        mUserName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));
    }

    @Override
    public void setData(List<History> history) {
        if (history.size() > 0) {
            mHistoryList.setVisibility(View.VISIBLE);
            mHistoryList.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            mNoPurchasesTextView.setVisibility(GONE);
            mNoPurchasesTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            mHistoryAdapter.setData(history);
        }
    }

    @Override
    public void logOut() {
        Utils.clearUsersData();
        Navigation.goToLoginActivity(this);
        finish();
    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Override
    public void onBackPressed() {
        mPresenter.cancelRunningRequest();
        super.onBackPressed();
    }
}
