package com.softjourn.sj_coin.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class ProductItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<ProductItemsAdapter.MachineViewHolder> implements Constants {

    private int mItemsView;

    private List<Product> mProduct;

    public ProductItemsAdapter(int view) {
        this.mItemsView = view;
    }

    public void setData(List<Product> data) {
        mProduct = data;
        notifyDataSetChanged();
    }

    @Override
    public MachineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (mItemsView == LIST_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_view_item, parent, false);
        } else {v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_machine_view_item, parent, false);}

        return new MachineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MachineViewHolder holder, int position) {
        final Product product = mProduct.get(position);

        holder.mProductName.setText(product.getName());
        holder.mProductPrice.setText(String.valueOf(product.getPrice()));
        holder.mProductPosition.setText(product.getPosition().getCellName());

        if (TextUtils.isEmpty(product.getImageUrl())) {
            Picasso.with(App.getContext()).load(R.drawable.softjourn_logo).into(holder.mProductImage);
        } else {
            PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE+product.getImageUrl()).into(holder.mProductImage);
        }
    }


    @Override
    public int getItemCount() {
        return mProduct == null ? 0 : mProduct.size();
    }

    public static class MachineViewHolder extends RecyclerView.ViewHolder {

        public TextView mProductPrice;
        public TextView mProductName;
        public TextView mProductPosition;
        public ImageView mProductImage;

        public MachineViewHolder(View v) {
            super(v);
            mProductImage = (ImageView) v.findViewById(R.id.layout_item_product_img);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mProductPosition = (TextView) v.findViewById(R.id.layout_item_product_position);
        }
    }

}
