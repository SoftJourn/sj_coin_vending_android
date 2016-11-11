package com.softjourn.sj_coin.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.softjourn.sj_coin.utils.ServerErrors;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity implements Const {

    private final EventBus mEventBus = EventBus.getDefault();

    protected boolean mProfileIsVisible = false;
    protected boolean mConfirmDialogIsVisible = false;

    private ProgressDialog mProgressDialog;

    protected Dialog mConfirmDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus.register(this);
        mProgressDialog = new ProgressDialog(this, R.style.Base_V7_Theme_AppCompat_Dialog);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                Navigation.goToVendingActivity(this);
                finish();
                return true;
            case R.id.allProducts:
                if (this.getLocalClassName().equals("activities.SeeAllActivity")) {
                    this.getFragmentManager().beginTransaction()
                            .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_ITEMS,0,0), TAG_ALL_PRODUCTS_FRAGMENT)
                            .commit();
                } else {
                    Navigation.goToSeeAllActivity(this, ALL_ITEMS);
                }
                return true;
            case R.id.favorites:
                if (this.getLocalClassName().equals("activities.SeeAllActivity")) {
                    this.getFragmentManager().beginTransaction()
                            .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES,0,0), TAG_FAVORITES_FRAGMENT)
                            .commit();
                } else {
                    Navigation.goToSeeAllActivity(this, FAVORITES);
                }
                return true;
            case R.id.profile:
                if (!mProfileIsVisible) {
                    Navigation.goToProfileActivity(this);
                    return true;
                } else {
                    return false;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    public void showProgress(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void onNoInternetAvailable() {
        showToast(getString(R.string.internet_turned_off));
        hideProgress();
    }

    public void showToast(String text) {
        Utils.showErrorToast(this, text);
    }

    protected void onCreateConfirmDialog(final Product product, final PurchaseContract.Presenter presenter) {
        mConfirmDialog = new Dialog(this);
        mConfirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mConfirmDialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        mConfirmDialog.setContentView(R.layout.dialog_confirm);

        // set the custom dialog components
        TextView text = (TextView) mConfirmDialog.findViewById(R.id.text);
        text.setText(String.format(getString(R.string.dialog_msg_confirm_buy_product), product.getName(), product.getPrice()));
        ImageView image = (ImageView) mConfirmDialog.findViewById(R.id.image);
        PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + product.getImageUrl()).into(image);

        if (!mConfirmDialog.isShowing()) {
            mConfirmDialog.getWindow().getAttributes().windowAnimations = R.style.ConfirmDialogAnimation;
            mConfirmDialogIsVisible = true;
            mConfirmDialog.show();
        }

        Button okButton = (Button) mConfirmDialog.findViewById(R.id.dialogButtonOK);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buyProduct(String.valueOf(product.getId()));
                mConfirmDialogIsVisible = false;
                mConfirmDialog.dismiss();
            }
        });

        Button cancelButton = (Button) mConfirmDialog.findViewById(R.id.dialogButtonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmDialogIsVisible = false;
                mConfirmDialog.dismiss();
            }
        });

        mConfirmDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mConfirmDialogIsVisible = false;
            }
        });

        mConfirmDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mConfirmDialogIsVisible = false;
            }
        });
    }

    public void onCreateErrorDialog(String message){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);

        // set the custom dialog components
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(message);

        Button cancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!dialog.isShowing()) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.ConfirmDialogAnimation;
            dialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final OnServerErrorEvent event) {
        hideProgress();
        onCreateErrorDialog(ServerErrors.showErrorMessage(event.getMessage()));
    }

    @Subscribe
    public void OnEvent(NoSubscriberEvent event) {
        hideProgress();
    }


}
