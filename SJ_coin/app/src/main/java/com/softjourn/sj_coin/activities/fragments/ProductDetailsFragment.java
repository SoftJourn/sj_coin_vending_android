package com.softjourn.sj_coin.activities.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.events.OnAddFavoriteEvent;
import com.softjourn.sj_coin.events.OnProductBuyClickEvent;
import com.softjourn.sj_coin.events.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.managers.DataManager;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.softjourn.sj_coin.utils.Const.URL_VENDING_SERVICE;

/**
 * Created by home on 04.12.2016.
 */

public class ProductDetailsFragment extends BottomSheetDialogFragment {


    TextView mProductName;
    TextView mProductLongDescription;
    ImageView mProductImage;
    TextView mProductPrice;
    RelativeLayout mLayout;

    ImageView mFavorites;
    TextView mBuyProduct;

    private DataManager mDataManager = new DataManager();
    private List<Product> sFavoritesList = mDataManager.loadFavorites();

    private Product mProduct;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //setStateText(newState);
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //setOffsetText(slideOffset);
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mProduct = getArguments().getParcelable("PRODUCT_EXTRAS");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View contentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(contentView, savedInstanceState);

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_product_details, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        mProductName = (TextView) contentView.findViewById(R.id.details_product_name);
        mProductLongDescription = (TextView) contentView.findViewById(R.id.details_product_description);
        mProductPrice = (TextView) contentView.findViewById(R.id.details_product_price);
        mProductImage = (ImageView) contentView.findViewById(R.id.details_product_image);
        mFavorites = (ImageView) contentView.findViewById(R.id.details_add_to_favorite);
        mBuyProduct = (TextView) contentView.findViewById(R.id.details_buy_product);

        mProductName.setText(mProduct.getName());
        mProductLongDescription.setText(mProduct.getDescription());
        mProductPrice.setText(String.format(getString(R.string.coins), mProduct.getPrice()));
        PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + mProduct.getImageUrl()).into(mProductImage);
        loadFavoriteIcon();

        mBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OnProductBuyClickEvent(mProduct));
            }
        });

        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnFavoriteClick();
            }
        });
    }

    private void loadFavoriteIcon() {

        mFavorites.setTag(false);
        if (sFavoritesList != null && sFavoritesList.size() > 0) {
            for (int i = 0; i < sFavoritesList.size(); i++) {
                if (sFavoritesList.get(i).getId().equals(mProduct.getId())) {
                    Picasso.with(App.getContext()).load(R.drawable.ic_favorite_pink).into(mFavorites);
                    mFavorites.setTag(true);
                    break;
                } else {
                    Picasso.with(App.getContext()).load(R.drawable.ic_favorite_border_white).into(mFavorites);
                    mFavorites.setTag(false);
                }
            }
        } else {
            Picasso.with(App.getContext()).load(R.drawable.ic_favorite_border_white).into(mFavorites);
        }
    }

    private void handleOnFavoriteClick() {
        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Boolean) mFavorites.getTag()) {
                    EventBus.getDefault().post(new OnAddFavoriteEvent(mProduct));
                } else {
                    EventBus.getDefault().post(new OnRemoveFavoriteEvent(mProduct));
                    if (NetworkUtils.isNetworkEnabled()) {
                        loadFavoriteIcon();
                    }
                }
            }
        });
    }
}
