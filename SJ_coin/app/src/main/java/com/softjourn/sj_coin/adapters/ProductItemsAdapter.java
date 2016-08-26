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
import com.softjourn.sj_coin.ProductsListSingleton;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Constants;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ProductItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<ProductItemsAdapter.MachineViewHolder> implements Constants {

    private String mRecyclerViewType;

    private List<Product> mProduct;

    public ProductItemsAdapter(@Nullable String recyclerViewType) {
        if (recyclerViewType != null) {
            mRecyclerViewType = recyclerViewType;
        } else {
            mRecyclerViewType = DEFAULT_RECYCLER_VIEW;
        }
    }

    public void setData(List<Product> data) {
        mProduct = data;
        notifyDataSetChanged();
    }

    @Override
    public MachineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (mRecyclerViewType) {
            case "DEFAULT":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_machine_view_item, parent, false);
                break;
            case "SEE_ALL":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_see_all_product_list, parent, false);
                break;
            case "SEE_ALL_SNACKS_DRINKS":
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_see_all_snacks_drinks_item, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_machine_view_item, parent, false);
        }
        return new MachineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MachineViewHolder holder, final int position) {
        final Product product = mProduct.get(position);

        final List<Product> data = ProductsListSingleton.getInstance().getData();

        //holder.mParentView.setTag(product.getId());
        holder.mProductName.setText(product.getName());
        holder.mProductPrice.setText(String.valueOf(product.getPrice()));

        if (holder.mProductDescription != null) {
            holder.mProductDescription.setText(product.getPosition().getCellName());
        }

        if (holder.mArrowImage != null) {
            holder.mArrowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OnProductItemClickEvent(data.get(position)));
                }
            });
        }

        if (holder.mParentView != null) {
            holder.mParentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OnProductItemClickEvent(data.get(position)));
                }
            });
        }

        if (holder.mBuyProduct != null) {
            holder.mBuyProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OnProductBuyClickEvent(data.get(position)));
                }
            });
        }

        if (holder.mAddFavorite != null) {
            holder.mAddFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OnAddFavoriteEvent(data.get(position)));
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
        return mProduct == null ? 0 : mProduct.size();
    }

    public static class MachineViewHolder extends RecyclerView.ViewHolder {

        public View mParentView;
        public TextView mProductPrice;
        public TextView mProductName;
        public TextView mBuyProduct;
        public TextView mProductDescription;

        public ImageView mProductImage;
        public ImageView mArrowImage;
        public ImageView mAddFavorite;

        public MachineViewHolder(View v) {
            super(v);
            mParentView = (View) v.findViewById(R.id.layout_item_product_parent_view);
            mProductImage = (ImageView) v.findViewById(R.id.layout_item_product_img);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mBuyProduct = (TextView) v.findViewById(R.id.layout_item_product_buy);
            mProductDescription = (TextView) v.findViewById(R.id.layout_item_product_description);
            mArrowImage = (ImageView) v.findViewById(R.id.imageViewArrowRight);
            mAddFavorite = (ImageView) v.findViewById(R.id.imageViewFavorite);
        }
    }

}
