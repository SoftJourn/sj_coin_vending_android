package com.softjourn.sj_coin.activities.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;

import static com.softjourn.sj_coin.utils.Const.URL_VENDING_SERVICE;

/**
 * Created by home on 04.12.2016.
 */

public class ProductDetailsFragment extends BottomSheetDialogFragment {

    private TextView mProductName;
    private TextView mProductLongDescription;
    private ImageView mProductImage;
    private TextView mProductText;

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
    private LinearLayoutManager mLinearLayoutManager;
    //private ApplicationAdapter mAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mProduct = getArguments().getParcelable("PRODUCT_EXTRAS");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View contentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(contentView, savedInstanceState);


//
//        recyclerView.setLayoutManager(mLinearLayoutManager);
//        recyclerView.setAdapter(mAdapter);
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
        mProductImage = (ImageView) contentView.findViewById(R.id.details_product_image);

        mProductName.setText(mProduct.getName());
        mProductLongDescription.setText(mProduct.getDescription());
        PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + mProduct.getImageUrl()).into(mProductImage);
        //mStateText = (TextView) contentView.findViewById(R.id.stateText);
    }

}
