package com.softjourn.sj_coin.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListNewProductsFragment;
import com.softjourn.sj_coin.adapters.PicassoTrustAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllActivity extends BaseActivity implements VendingContract.View,Constants, Extras {

    private VendingContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        mPresenter = new VendingPresenter(this);

        setTitle(getIntent().getStringExtra(EXTRAS_CATEGORY));

        switch (getIntent().getStringExtra(EXTRAS_CATEGORY)){
            case SNACKS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListSnacksFragment.newInstance(), TAG_PRODUCTS_SNACKS_FRAGMENT)
                        .commit();
                break;
            case DRINKS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductListDrinksFragment.newInstance(), TAG_PRODUCTS_DRINKS_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListBestSellersFragment.newInstance(), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case NEW_PRODUCTS:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListNewProductsFragment.newInstance(), TAG_PRODUCTS_NEW_PRODUCT_FRAGMENT)
                        .commit();
                break;
            case LAST_PURCHASES:
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListLastPurchasesFragment.newInstance(), TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT)
                        .commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);
        return true;
    }

    @Override
    public void loadData(List<Drink> drinks,List<Snack> snacks) {

    }

    @Override
    public void loadNewProductsData(List<NewProduct> data) {

    }

    @Override
    public void loadBestSellerData(List<BestSeller> data) {

    }

    @Override
    public void loadMyLastPurchaseData(List<MyLastPurchase> data) {

    }

    @Override
    public void loadSnackData(List<Snack> data) {

    }

    @Override
    public void loadDrinkData(List<Drink> data) {

    }

    @Override
    public void navigateToBuyProduct(CustomizedProduct product) {
        onCreateDialog(product);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<CustomizedProduct> product) {

    }

    @Override
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {

    }

    private void onCreateDialog(final CustomizedProduct product){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm);
        dialog.setTitle("Title...");

        // set the custom dialog components
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Buy "+product.getName() + " for " + product.getPrice() + " coins?");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + product.getImageUrl()).into(image);

        Button okButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.buyProduct(String.valueOf(product.getId()));
            }
        });

        Button cancelButton = (Button)dialog.findViewById(R.id.dialogButtonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event){
        navigateToBuyProduct(event.buyProduct());
    }
}
