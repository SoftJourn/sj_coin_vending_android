package com.softjourn.sj_coin.activities.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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

    private Target target;

    private Bitmap mImageBitmap;

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

        loadImage();

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
        //mLayout = (RelativeLayout) contentView.findViewById(R.id.layout_header);

        //changeColorPalette();
        mProductName.setText(mProduct.getName());
        mProductLongDescription.setText(mProduct.getDescription());
        mProductPrice.setText(String.format(getString(R.string.coins), mProduct.getPrice()));
        PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + mProduct.getImageUrl()).into(mProductImage);
    }

    private void loadImage() {
        target = new Target() {
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                mImageBitmap = bitmap;
            }

            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }

            public void onBitmapFailed(Drawable errorDrawable) {
            }
        };
        PicassoTrustAdapter.getInstance(App.getContext())
                .load(URL_VENDING_SERVICE + mProduct.getImageUrl())
                .into(target);
    }


    private void changeColorPalette() {
        int numberOfColors = 10;

        Palette.from(mImageBitmap).maximumColorCount(numberOfColors).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null) {
                    // If we have a vibrant color
                    // update Views
                    mLayout.setBackgroundColor(vibrant.getRgb());
                    mProductName.setTextColor(vibrant.getTitleTextColor());

                    if (palette.getDarkVibrantSwatch() != null) {
                        mProductPrice.setTextColor(palette.getDarkVibrantSwatch().getRgb());
                    } else {
                        mProductPrice.setTextColor(palette.getDominantSwatch().getRgb());
                    }
                }
            }
        });
    }

}
