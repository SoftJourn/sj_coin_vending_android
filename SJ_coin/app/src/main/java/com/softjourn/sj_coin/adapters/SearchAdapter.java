package com.softjourn.sj_coin.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.model.CustomizedProduct;

import java.util.List;

public class SearchAdapter extends CursorAdapter {

    private List<CustomizedProduct> items;

    private TextView text;

    public View mParentView;
    public TextView mProductPrice;
    public TextView mProductName;
    public TextView mBuyProduct;
    public TextView mProductDescription;

    public ImageView mProductImage;
    public ImageView mAddFavorite;

    public SearchAdapter(Context context, Cursor cursor, List<CustomizedProduct> items) {

        super(context, cursor, false);

        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mProductName.setText(items.get(cursor.getPosition()).getName());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.recycler_see_all_item, parent, false);

        mParentView = (View) v.findViewById(R.id.layout_item_product_parent_view);
        mProductImage = (ImageView) v.findViewById(R.id.layout_item_product_img);
        mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
        mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
        mBuyProduct = (TextView) v.findViewById(R.id.layout_item_product_buy);
        mProductDescription = (TextView) v.findViewById(R.id.layout_item_product_description);
        mAddFavorite = (ImageView) v.findViewById(R.id.imageViewFavorite);

        return v;

    }

}
