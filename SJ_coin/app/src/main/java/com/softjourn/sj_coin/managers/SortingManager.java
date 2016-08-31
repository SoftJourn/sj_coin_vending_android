package com.softjourn.sj_coin.managers;


import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import com.softjourn.sj_coin.model.CustomizedProduct;

public class SortingManager {
    private SortedList<CustomizedProduct> mProduct;

    RecyclerView.Adapter adapter;

    public SortingManager(RecyclerView.Adapter adapter, SortedList<CustomizedProduct> list) {
        this.adapter = adapter;
        this.mProduct = list;
    }


    public SortedList<CustomizedProduct> sortByName() {
        return mProduct = new SortedList<CustomizedProduct>(CustomizedProduct.class, new SortedList.Callback<CustomizedProduct>() {
            @Override
            public int compare(CustomizedProduct o1, CustomizedProduct o2) {
                return o1.getName().compareTo(o2.getName());
            }

            @Override
            public void onInserted(int position, int count) {
                adapter.notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                adapter.notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                adapter.notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(CustomizedProduct oldItem, CustomizedProduct newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areItemsTheSame(CustomizedProduct item1, CustomizedProduct item2) {
                return item1.getId() == item2.getId();
            }
        });
    }
}
