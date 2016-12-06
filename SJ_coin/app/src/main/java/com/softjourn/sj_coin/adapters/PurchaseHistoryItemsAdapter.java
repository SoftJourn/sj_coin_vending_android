package com.softjourn.sj_coin.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.TimeUtils;

import java.util.List;

public class PurchaseHistoryItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<PurchaseHistoryItemsAdapter.HistoryViewHolder> implements Const {

    private List<History> mHistoryList;

    public void setData(List<History> history) {
        mHistoryList = history;
        notifyDataSetChanged();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_purchase_history, parent, false);
        //v.startAnimation(AnimationUtils.loadAnimation(App.getContext(),R.anim.slide_from_top));
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        final History historyItem = mHistoryList.get(position);

        holder.mProductName.setText(historyItem.getName());
        holder.mProductPrice.setText(String.format(App.getContext().getResources().getString(R.string.coins), historyItem.getPrice()));
        holder.mPurchaseDate.setText(TimeUtils.getPrettyTime(historyItem.getTime()));
    }

    @Override
    public int getItemCount() {
        return mHistoryList == null ? 0 : mHistoryList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {

        final View mParentView;
        final TextView mProductPrice;
        final TextView mProductName;
        final TextView mPurchaseDate;

        HistoryViewHolder(View v) {
            super(v);
            mParentView = v.findViewById(R.id.layout_item_parent_view);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mPurchaseDate = (TextView) v.findViewById(R.id.layout_item_purchase_date);
        }
    }
}
