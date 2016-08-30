package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.PicassoTrustAdapter;
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
import com.softjourn.sj_coin.utils.Extras;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends BaseActivity implements VendingContract.View,Extras {

    CustomizedProduct mProduct;

    private VendingContract.Presenter mPresenter;

    @Bind(R.id.productImageView)
    ImageView mImageView;

    @Bind(R.id.productName)
    TextView mProductName;

    @Bind(R.id.productBuy)
    TextView mProductBuy;

    @Bind(R.id.productDescription)
    TextView mProductDescription;

    @OnClick(R.id.productBuy)
    public void buyProduct(){
        buyProductByID();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ButterKnife.bind(this);

        mProduct = getIntent().getParcelableExtra(EXTRAS_CONCRETE_PRODUCT);

        mProductName.setText(mProduct.getName());
        //mProductDescription.setText(mProduct.getPosition().getCellName());

        if (TextUtils.isEmpty(mProduct.getImageUrl())) {
            Picasso.with(App.getContext()).load(R.drawable.softjourn_logo).into(mImageView);
        } else {
            PicassoTrustAdapter.getInstance(App.getContext()).load(URL_COIN_SERVICE + mProduct.getImageUrl()).into(mImageView);
        }

        mPresenter = new VendingPresenter(this);
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

    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    private void buyProductByID() {
        mPresenter.buyProduct(String.valueOf(mProduct.getId()));
    }
}
