package com.softjourn.sj_coin.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.events.OnAddFavoriteEvent;
import com.softjourn.sj_coin.events.OnProductBuyClickEvent;
import com.softjourn.sj_coin.events.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.events.OnRemoveItemFromCategoryFavorite;
import com.softjourn.sj_coin.managers.DataManager;
import com.softjourn.sj_coin.ui.activities.SeeAllActivity;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.softjourn.sj_coin.utils.Preferences;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.softjourn.sj_coin.utils.Const.FAVORITES;
import static com.softjourn.sj_coin.utils.Const.URL_VENDING_SERVICE;
import static com.softjourn.sj_coin.utils.Const.USER_BALANCE_PREFERENCES_KEY;

/**
 * Created by home on 04.12.2016.
 */

public class ProductDetailsFragment extends BottomSheetDialogFragment {

    TextView mProductName;
    TextView mProductLongDescription;
    ImageView mProductImage;
    TextView mProductPrice;

    ImageView mFavorites;
    TextView mBuyProduct;

    private boolean isRemovedFromFavorite = false;

    private DataManager mDataManager = new DataManager();
    private List<Product> sFavoritesList = mDataManager.loadFavorites();

    private Product mProduct;

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
        setBuyProductButton();

        mBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBuyButton();
            }
        });

        mFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnFavoriteClick();
            }
        });
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if ((getActivity()).getTitle().equals(FAVORITES) && isRemovedFromFavorite) {
            EventBus.getDefault().post(new OnRemoveItemFromCategoryFavorite(mProduct.getId()));
        }
    }

    private void setBuyProductButton() {
        if (mProduct.getPrice() > Integer.parseInt(Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY))) {
            mBuyProduct.setTextColor(ContextCompat.getColor(App.getContext(), R.color.colorDivider));
        } else {
            mBuyProduct.setTextColor(ContextCompat.getColor(App.getContext(), R.color.white));
        }
    }

    private void handleBuyButton() {
        if (mProduct.getPrice() < Integer.parseInt(Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY))) {
            EventBus.getDefault().post(new OnProductBuyClickEvent(mProduct));
        } else {
            ((SeeAllActivity) getActivity()).onCreateErrorDialog(getString(R.string.server_error_40901));
        }
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
        if (NetworkUtils.isNetworkEnabled()) {
            if (!(Boolean) mFavorites.getTag()) {
                EventBus.getDefault().post(new OnAddFavoriteEvent(mProduct));
                Picasso.with(App.getContext()).load(R.drawable.ic_favorite_pink).into(mFavorites);
                mFavorites.setTag(true);
                isRemovedFromFavorite = false;
            } else {
                EventBus.getDefault().post(new OnRemoveFavoriteEvent(mProduct));
                Picasso.with(App.getContext()).load(R.drawable.ic_favorite_border_white).into(mFavorites);
                mFavorites.setTag(false);
                isRemovedFromFavorite = true;
            }
        } else {
            ((SeeAllActivity) getActivity()).showNoInternetError();
        }
    }
}
