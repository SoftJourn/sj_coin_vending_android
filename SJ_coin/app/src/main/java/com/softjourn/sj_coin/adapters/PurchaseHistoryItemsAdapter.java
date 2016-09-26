package com.softjourn.sj_coin.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.utils.Const;

import java.util.List;

public class PurchaseHistoryItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<PurchaseHistoryItemsAdapter.HistoryViewHolder> implements Const {

    private List<History> mList;

    private String mCoins = " " + App.getContext().getString(R.string.item_coins);


    public void setData(List<History> data){
        mList = data;
        notifyDataSetChanged();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_purchase_history, parent, false);

        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        final History product = mList.get(position);

        holder.mProductName.setText(product.getName());
        holder.mProductPrice.setText(product.getPrice() + mCoins);
        holder.mPurchaseDate.setText(product.getDate());

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        public View mParentView;
        public TextView mProductPrice;
        public TextView mProductName;
        public TextView mPurchaseDate;

        public HistoryViewHolder(View v) {
            super(v);
            mParentView = (View) v.findViewById(R.id.layout_item_parent_view);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mPurchaseDate = (TextView) v.findViewById(R.id.layout_item_purchase_date);
        }
    }
}
