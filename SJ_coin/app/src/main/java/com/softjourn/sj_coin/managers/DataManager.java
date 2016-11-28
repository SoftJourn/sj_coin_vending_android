package com.softjourn.sj_coin.managers;

import com.softjourn.sj_coin.api_models.products.Categories;
import com.softjourn.sj_coin.api_models.products.Product;
import com.softjourn.sj_coin.dataStorage.FavoritesStorage;
import com.softjourn.sj_coin.dataStorage.FeaturesStorage;
import com.softjourn.sj_coin.dataStorage.ProductsStorage;
import com.softjourn.sj_coin.utils.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by omartynets on 04.11.2016.
 * To make local manipulations with data on device
 * till application is not closed
 */

public class DataManager implements Const {

    public List<Product> loadLocalProductList() {
        return sortBeforeReturning(ProductsStorage.getInstance().getData());
    }

    public List<Product> loadBestSellers() {
        List<Product> bestSellersProductList = new ArrayList<>();
        Integer bestSellerID;
        Integer productID;

        for (int i = 0; i < FeaturesStorage.getInstance().getData().getBestSellers().size(); i++) {
            bestSellerID = FeaturesStorage.getInstance().getData().getBestSellers().get(i);
            for (int j = 0; j < ProductsStorage.getInstance().getData().size(); j++) {
                productID = ProductsStorage.getInstance().getData().get(j).getId();
                if (bestSellerID.equals(productID)) {
                    bestSellersProductList.add(ProductsStorage.getInstance().getData().get(j));
                    break;
                }
            }
        }
        return sortBeforeReturning(bestSellersProductList);
    }

    public List<Product> loadLastAdded() {
        List<Product> lastAddedProductList = new ArrayList<>();
        Integer lastAddedID;
        Integer productID;

        for (int i = 0; i < FeaturesStorage.getInstance().getData().getLastAdded().size(); i++) {
            lastAddedID = FeaturesStorage.getInstance().getData().getLastAdded().get(i);
            for (int j = 0; j < ProductsStorage.getInstance().getData().size(); j++) {
                productID = ProductsStorage.getInstance().getData().get(j).getId();
                if (lastAddedID.equals(productID)) {
                    lastAddedProductList.add(ProductsStorage.getInstance().getData().get(j));
                    break;
                }
            }
        }
        return sortBeforeReturning(lastAddedProductList);
    }

    public List<Product> loadFavorites() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < FavoritesStorage.getInstance().getData().size(); i++) {
            productList.add(new Product(FavoritesStorage.getInstance().getData().get(i)));
        }
        return sortBeforeReturning(productList);
    }

    public List<Product> loadProductsFromDB(String category) {
        List<Product> productList = new ArrayList<>();

        for (int j = 0; j < ProductsStorage.getInstance().getData().size(); j++) {
            if (ProductsStorage.getInstance().getData().get(j).getCategory().getName().equals(category)) {
                productList.add(ProductsStorage.getInstance().getData().get(j));
            }
        }
        return sortBeforeReturning(productList);
    }

    public List<Categories> loadCategories() {
        return FeaturesStorage.getInstance().getData().getCategories().size() > 0 ? FeaturesStorage.getInstance().getData().getCategories() : new ArrayList<Categories>();
    }

    public boolean isSingleProductPresent(Integer id) {
        for (Product product : ProductsStorage.getInstance().getData()) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addToFavorites(Integer id) {
        FavoritesStorage.getInstance().LocalAddToFavorites(id);
    }

    public void removeFromFavorites(Integer id) {
        FavoritesStorage.getInstance().removeFromFavoritesLocal(id);
    }

    public List<Product> getConcreteCategory(String productsCategory) {
        List<Product> product;
        switch (productsCategory) {
            case ALL_ITEMS:
                product = loadLocalProductList();
                break;
            case BEST_SELLERS:
                product = loadBestSellers();
                break;
            case LAST_ADDED:
                product = loadLastAdded();
                break;
            case FAVORITES:
                product = loadFavorites();
                break;
            default:
                product = loadProductsFromDB(productsCategory);
                break;
        }
        return product;
    }

    /**
     * List of products should be sorted by Name ascending
     * before showing on the screen.
     *
     * @param productList = list of products to be displayed.
     * @return
     */
    private List<Product> sortBeforeReturning (List<Product> productList) {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        return productList;
    }

}
