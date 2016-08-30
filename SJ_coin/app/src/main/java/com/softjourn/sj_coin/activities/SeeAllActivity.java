package com.softjourn.sj_coin.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductListDrinksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductListSnacksFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListBestSellersFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListLastPurchasesFragment;
import com.softjourn.sj_coin.activities.fragments.ProductsListNewProductsFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;

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
    public void loadData(List<Product> data) {

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
    public void showToastMessage(String message) {

    }

    @Override
    public void showNoInternetError() {

    }

    private void onCreateDialog(final CustomizedProduct product){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Purchase confirmation");
        builder.setMessage("Buy " + product.getName());

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.buyProduct(String.valueOf(product.getId()));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog confirmationDialog = builder.create();
        confirmationDialog.show();
    }
}
