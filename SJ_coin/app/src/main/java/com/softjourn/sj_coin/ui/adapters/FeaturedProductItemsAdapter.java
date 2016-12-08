package com.softjourn.sj_coin.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.events.OnAddFavoriteEvent;
import com.softjourn.sj_coin.events.OnProductBuyClickEvent;
import com.softjourn.sj_coin.events.OnProductDetailsClick;
import com.softjourn.sj_coin.events.OnProductItemClickEvent;
import com.softjourn.sj_coin.events.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.events.OnRemovedLastFavoriteEvent;
import com.softjourn.sj_coin.managers.DataManager;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FeaturedProductItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<FeaturedProductItemsAdapter.FeaturedViewHolder> implements Const,
        Filterable {

    private final String mRecyclerViewType;
    private final String mCategory;
    private final String mCoins;

    private List<Product> mListProducts = new ArrayList<>();
    private DataManager mDataManager = new DataManager();
    private List<Product> mOriginal = new ArrayList<>();
    private List<Product> sFavoritesList = mDataManager.loadFavorites();
    private Context mContext;

    public FeaturedProductItemsAdapter(@Nullable String featureCategory, @Nullable String recyclerViewType, Context context) {

        mContext = context;

        if (featureCategory != null) {
            mCategory = featureCategory;
        } else {
            mCategory = "";
        }

        if (recyclerViewType != null) {
            mRecyclerViewType = recyclerViewType;
        } else {
            mRecyclerViewType = DEFAULT_RECYCLER_VIEW;
        }
        mCoins = " " + mContext.getString(R.string.item_coins);
    }

    public void notifyDataChanges(){
        sFavoritesList = mDataManager.loadFavorites();
        notifyDataSetChanged();
    }

    public void setData(List<Product> data) {
        mListProducts = new ArrayList<>(data);
        mOriginal = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public void removeItem(int id) {
        for (int i = 0; i < mListProducts.size(); i++) {
            if (mListProducts.get(i).getId().equals(id)) {
                mListProducts.remove(mListProducts.get(i));
                notifyItemRemoved(i);
                notifyItemRangeChanged(0, getItemCount() + 1);
            }
        }
    }

    @Override
    public FeaturedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        assert mRecyclerViewType != null;
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
    public void onBindViewHolder(final FeaturedViewHolder holder, final int position) {

        Product product = mListProducts.get(holder.getAdapterPosition());

        boolean isCurrentProductInMachine = mDataManager.isSingleProductPresent(product.getId());

        holder.mProductName.setText(mListProducts.get(holder.getAdapterPosition()).getName());
        holder.mProductPrice.setText(String.valueOf(product.getPrice()) + mCoins);

        if (holder.mProductDescription != null) {
            holder.mProductDescription.setText(product.getDescription());
        }

        if (holder.mParentView != null) {
                holder.mParentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OnProductItemClickEvent(mListProducts.get(holder.getAdapterPosition())));
                    }
                });
            }
        if (holder.mParentViewSeeAll!=null) {
            holder.mParentViewSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OnProductDetailsClick(mListProducts.get(holder.getAdapterPosition())));
                }
            });
        }

        /**
         * Changing color of Buy TextView depends on is product in chosen machine or not
         * Also if product is not available in current machine there is no listener for click on TextView.
         */
            if (holder.mBuyProduct != null) {
                if (isCurrentProductInMachine) {
                    holder.mBuyProduct.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlue));
                } else {
                    holder.mBuyProduct.setTextColor(ContextCompat.getColor(mContext, R.color.colorScreenBackground));
                }
                holder.mBuyProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OnProductBuyClickEvent(mListProducts.get(holder.getAdapterPosition())));
                    }
                });
            }
        /**
         * Here We compare ID of product from general products list
         * and ID of favorites list
         * When IDs are the same we change image of Favorites accordingly.
         * Also we differentiate callbacks on Click on Image depends on
         * if this product is already in Favorites to remove or add product
         * to favorites list
         */
        if (holder.mAddFavorite != null)
        {
            holder.mAddFavorite.setTag(false);
            if (sFavoritesList != null && sFavoritesList.size() > 0) {
                for (int i = 0; i < sFavoritesList.size(); i++) {
                    if (sFavoritesList.get(i).getId().equals(product.getId())) {
                        Picasso.with(mContext).load(R.drawable.ic_favorite_filled).into(holder.mAddFavorite);
                        holder.mAddFavorite.setTag(true);
                        break;
                    } else {
                        Picasso.with(mContext).load(R.drawable.ic_favorite_border).into(holder.mAddFavorite);
                        holder.mAddFavorite.setTag(false);
                    }
                }
            } else {
                Picasso.with(mContext).load(R.drawable.ic_favorite_border).into(holder.mAddFavorite);
            }
            holder.mAddFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(Boolean) holder.mAddFavorite.getTag()) {
                        EventBus.getDefault().post(new OnAddFavoriteEvent(mListProducts.get(holder.getAdapterPosition())));
                    } else {
                            EventBus.getDefault().post(new OnRemoveFavoriteEvent(mListProducts.get(holder.getAdapterPosition())));
                        if (NetworkUtils.isNetworkEnabled()) {
                            if (mCategory.equals(FAVORITES)) {
                                mListProducts.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(0, getItemCount() + 1);
                                if (getItemCount() < 1) {
                                    EventBus.getDefault().post(new OnRemovedLastFavoriteEvent(mListProducts));
                                }
                            }
                        }
                    }
                }
            });
        }

        /**
         * Changing Alpha of image depends on is product present in chosen machine or not
         */
        if (TextUtils.isEmpty(product.getImageUrl()))
        {
            Picasso.with(mContext).load(R.drawable.logo).into(holder.mProductImage);
            holder.mProductImage.setAlpha(1.0f);
        } else  {
            PicassoTrustAdapter.getInstance(mContext).load(URL_VENDING_SERVICE + mListProducts.get(holder.getAdapterPosition()).getImageUrl()).into(holder.mProductImage);
            if (!isCurrentProductInMachine) {
                holder.mProductImage.setAlpha(0.3f);
            } else {
                holder.mProductImage.setAlpha(1.0f);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListProducts == null ? 0 : mListProducts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    /**
     * Filters products in list by given string in SearchView
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Product> results = new ArrayList<>();
                if (mOriginal == null || mOriginal.size() <= 0)
                    mOriginal = mListProducts;
                if (constraint != null) {
                    if (mOriginal != null & mOriginal.size() > 0) {
                        for (final Product g : mOriginal) {
                            if (g.getName().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListProducts = (ArrayList<Product>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        final View mParentView;
        final View mParentViewSeeAll;
        final TextView mProductPrice;
        final TextView mProductName;
        final TextView mBuyProduct;
        final TextView mProductDescription;

        final ImageView mProductImage;
        final ImageView mAddFavorite;

        FeaturedViewHolder(View v) {
            super(v);
            mParentViewSeeAll =  v.findViewById(R.id.layout_item_parent_view);
            mParentView = v.findViewById(R.id.layout_item_product_parent_view);
            mProductImage = (ImageView) v.findViewById(R.id.layout_item_product_img);
            mProductPrice = (TextView) v.findViewById(R.id.layout_item_product_price);
            mProductName = (TextView) v.findViewById(R.id.layout_item_product_name);
            mBuyProduct = (TextView) v.findViewById(R.id.layout_item_product_buy);
            mProductDescription = (TextView) v.findViewById(R.id.layout_item_product_description);
            mAddFavorite = (ImageView) v.findViewById(R.id.imageViewFavorite);
        }
    }
}
