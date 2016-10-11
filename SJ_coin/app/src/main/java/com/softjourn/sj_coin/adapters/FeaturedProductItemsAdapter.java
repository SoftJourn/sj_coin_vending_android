package com.softjourn.sj_coin.adapters;


import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnProductItemClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.RealmProductWrapper;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.PicassoTrustAdapter;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmQuery;

public class FeaturedProductItemsAdapter extends
        android.support.v7.widget.RecyclerView.Adapter<FeaturedProductItemsAdapter.FeaturedViewHolder> implements Const,
        Filterable {

    private final String mRecyclerViewType;

    private List<Product> mListProducts = new ArrayList<>();

    private List<RealmProductWrapper> mRealmProductWrapperList = new ArrayList<>();
    private List<RealmProductWrapper> mRealmProductWrapperOriginal = new ArrayList<>();

    private final String mCoins = " " + App.getContext().getString(R.string.item_coins);

    public FeaturedProductItemsAdapter(@Nullable String featureCategory, @Nullable String recyclerViewType) {

        if (recyclerViewType != null) {
            mRecyclerViewType = recyclerViewType;
        } else {
            mRecyclerViewType = DEFAULT_RECYCLER_VIEW;
        }

    }

    public void setData(List<Product> data) {
        mListProducts = new ArrayList<>(data);
        fromProductToRealmProductList();
        notifyDataSetChanged();
    }

    private void fromProductToRealmProductList() {
        for (Product products : mListProducts) {
            RealmProductWrapper currentProduct = new RealmProductWrapper(products);
            mRealmProductWrapperList.add(currentProduct);
            mRealmProductWrapperOriginal.add(currentProduct);
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
    public void onBindViewHolder(final FeaturedViewHolder holder, int position) {

        List<Product> sFavoritesList = RealmController.getInstance().getProductsFromStaticCategory(FAVORITES);

        final Product product = mListProducts.get(position);

        holder.mProductName.setText(product.getName());
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

        if (holder.mBuyProduct != null) {
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
        if (holder.mAddFavorite != null) {
            holder.mAddFavorite.setTag(false);
            if (sFavoritesList !=null && sFavoritesList.size() > 0) {
                for (int i = 0; i < sFavoritesList.size(); i++) {
                    if (Objects.equals(sFavoritesList.get(i).getId(), product.getId())) {
                        Picasso.with(App.getContext()).load(R.drawable.ic_favorite_filled).into(holder.mAddFavorite);
                        holder.mAddFavorite.setTag(true);
                        break;
                    } else {
                        Picasso.with(App.getContext()).load(R.drawable.ic_favorite_border).into(holder.mAddFavorite);
                        holder.mAddFavorite.setTag(false);
                    }
                }
            } else {
                Picasso.with(App.getContext()).load(R.drawable.ic_favorite_border).into(holder.mAddFavorite);
            }
            holder.mAddFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(Boolean) holder.mAddFavorite.getTag()) {
                        EventBus.getDefault().post(new OnAddFavoriteEvent(mListProducts.get(holder.getAdapterPosition())));
                    } else {
                        EventBus.getDefault().post(new OnRemoveFavoriteEvent(mListProducts.get(holder.getAdapterPosition())));
                    }
                }
            });
        }

        if (TextUtils.isEmpty(product.getImageUrl())) {
            Picasso.with(App.getContext()).load(R.drawable.logo).into(holder.mProductImage);
        } else {
            PicassoTrustAdapter.getInstance(App.getContext()).load(URL_VENDING_SERVICE + product.getImageUrl()).into(holder.mProductImage);
        }
    }


    @Override
    public int getItemCount() {
        return mListProducts == null ? 0 : mListProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<RealmProductWrapper> results = new ArrayList<>();
                if (mRealmProductWrapperOriginal == null || mRealmProductWrapperOriginal.size() <= 0)
                    mRealmProductWrapperOriginal = new ArrayList<>(mRealmProductWrapperList);
                if (constraint != null) {
                    if (mRealmProductWrapperOriginal != null & mRealmProductWrapperOriginal.size() > 0) {
                        for (final RealmProductWrapper g : mRealmProductWrapperOriginal) {
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
                mRealmProductWrapperList = (ArrayList<RealmProductWrapper>) results.values;
                if (mRealmProductWrapperList.size() > 0) {
                    List<Integer> list = new ArrayList<>();
                    for (RealmProductWrapper i : mRealmProductWrapperList) {
                        list.add(i.getId());
                    }
                    Realm realm = Realm.getDefaultInstance();
                    RealmQuery<Product> query = realm.where(Product.class).in("id", list.toArray(new Integer[list.size()]));
                    mListProducts = query.findAll();

                } else {
                    mListProducts = new ArrayList<>();
                }
                notifyDataSetChanged();
            }
        };
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        public final View mParentView;
        public final TextView mProductPrice;
        public final TextView mProductName;
        public final TextView mBuyProduct;
        public final TextView mProductDescription;

        public final ImageView mProductImage;
        public final ImageView mAddFavorite;

        public FeaturedViewHolder(View v) {
            super(v);
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
