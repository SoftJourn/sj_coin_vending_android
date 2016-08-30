package com.softjourn.sj_coin.adapters;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.utils.Constants;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FeaturedProductItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<FeaturedProductItemsAdapter.FeaturedViewHolder> implements Constants {

    private String mProductCategory;

    private String mRecyclerViewType;

    private List<CustomizedProduct> mListProducts;

    private String mCoins = " "+App.getContext().getString(R.string.item_coins);

    public FeaturedProductItemsAdapter(String featureCategory, @Nullable String recyclerViewType) {
        this.mProductCategory = featureCategory;

        if (recyclerViewType != null) {
            mRecyclerViewType = recyclerViewType;
        } else {
            mRecyclerViewType = DEFAULT_RECYCLER_VIEW;
        }
    }

    public void setNewProductData(List<NewProduct> data) {
        mListProducts=new ArrayList<CustomizedProduct>();
        for(int i=0; i<data.size();i++){
            mListProducts.add(new CustomizedProduct(data.get(i)));
        }
    }

    public void setBestSellerData(List<BestSeller> data) {
        mListProducts=new ArrayList<CustomizedProduct>();
        for(int i=0; i<data.size();i++){
            mListProducts.add(new CustomizedProduct(data.get(i)));
        }
    }

    public void setMyLastPurchaseData(List<MyLastPurchase> data) {
        mListProducts=new ArrayList<CustomizedProduct>();
        for(int i=0; i<data.size();i++){
            mListProducts.add(new CustomizedProduct(data.get(i)));
        }
    }

    public void setSnackData(List<Snack> data) {
        mListProducts=new ArrayList<CustomizedProduct>();
        for(int i=0; i<data.size();i++){
            mListProducts.add(new CustomizedProduct(data.get(i)));
        }
    }

    public void setDrinkData(List<Drink> data) {
        mListProducts=new ArrayList<CustomizedProduct>();
        for(int i=0; i<data.size();i++){
            mListProducts.add(new CustomizedProduct(data.get(i)));
        }
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (mRecyclerViewType) {
            case "DEFAULT":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_machine_view_item, parent, false);
                break;
            case "SEE_ALL_SNACKS_DRINKS":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_see_all_item, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_machine_view_item, parent, false);
        }
        return new FeaturedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeaturedViewHolder holder, final int position) {

        final CustomizedProduct product = mListProducts.get(position);

            holder.mProductName.setTag(product.getId());
            holder.mProductName.setText(product.getName());
            holder.mProductPrice.setText(String.valueOf(product.getPrice())+mCoins);

            if (holder.mProductDescription != null) {
                holder.mProductDescription.setText(product.getDescription());
            }

            if (holder.mParentView != null) {
                holder.mParentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OnProductItemClickEvent(mListProducts.get(position)));
                    }
                });
            }

            if (holder.mBuyProduct != null) {
                holder.mBuyProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OnProductBuyClickEvent(mListProducts.get(position)));
                    }
                });
            }

            if (holder.mAddFavorite != null) {
                holder.mAddFavorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OnAddFavoriteEvent(mListProducts.get(position)));
                    }
                });
            }

            if (TextUtils.isEmpty(product.getImageUrl())) {
                Picasso.with(App.getContext()).load(R.drawable.softjourn_logo).into(holder.mProductImage);
            } else {
                PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + product.getImageUrl()).into(holder.mProductImage);
            }
        }


    @Override
    public int getItemCount() {
        return mListProducts == null ? 0 : mListProducts.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        public View mParentView;
        public TextView mProductPrice;
        public TextView mProductName;
        public TextView mBuyProduct;
        public TextView mProductDescription;

        public ImageView mProductImage;
        public ImageView mAddFavorite;

        public FeaturedViewHolder(View v) {
            super(v);
            mParentView = (View) v.findViewById(R.id.layout_item_product_parent_view);
            mProductImage = (ImageView) v.findViewById(R.id.layout_item_product_img);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mBuyProduct = (TextView) v.findViewById(R.id.layout_item_product_buy);
            mProductDescription = (TextView) v.findViewById(R.id.layout_item_product_description);
            mAddFavorite = (ImageView) v.findViewById(R.id.imageViewFavorite);
        }
    }
}
