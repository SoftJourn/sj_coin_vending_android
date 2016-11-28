package com.softjourn.sj_coin.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Preferences;

import java.util.List;

public class SelectMachineListAdapter extends ArrayAdapter<String> {

    private List<String> mItems;

    private Context mContext;

    public SelectMachineListAdapter(Context context, int resource, List<String> list){
        super(context,resource,list);
        mItems = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater li;
            li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.select_machine_text_view, null);
        }

        String name = getItem(position);

        TextView machineName = (TextView)view.findViewById(R.id.text1);
        machineName.setText(name);

        if (name.equals(Preferences.retrieveStringObject(Const.SELECTED_MACHINE_NAME))) {
            machineName.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlue));
        }
        else {
            machineName.setTextColor(ContextCompat.getColor(mContext,R.color.menuBackground));
        }
        return view;
    }
}
