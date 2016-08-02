package com.softjourn.sj_coin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.model.Machines.Field;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class MachineItemsAdapter extends android.support.v7.widget.RecyclerView.Adapter<MachineItemsAdapter.MachineViewHolder> {

    private Field mField;

    public MachineItemsAdapter(Field field){
        this.mField = field;
    }

    @Override
    public MachineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);

        MachineViewHolder machineViewHolder = new MachineViewHolder(v);

        return machineViewHolder;
    }

    @Override
    public void onBindViewHolder(MachineViewHolder holder, int position) {
        holder.mFieldID.setText(mField.getInternalId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MachineViewHolder extends RecyclerView.ViewHolder {

        public TextView mFieldID;

        public MachineViewHolder(View v) {
            super(v);
            mFieldID = (TextView)v.findViewById(R.id.layout_item_field_id);
        }
    }

}
