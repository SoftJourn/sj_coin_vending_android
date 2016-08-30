package com.softjourn.sj_coin.model;


import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.MyLastPurchase;
import com.softjourn.sj_coin.model.products.NewProduct;
import com.softjourn.sj_coin.model.products.Snack;

public class CustomizedProduct {

    private int id;
    private int price;
    private String name;
    private String imageUrl;
    private String description;
    private String category;

    public CustomizedProduct(NewProduct product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(MyLastPurchase product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(BestSeller product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(Snack product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public CustomizedProduct(Drink product){
        this.id = product.id;
        this.description = product.description;
        this.name = product.name;
        this.price = product.price;
        this.imageUrl = product.imageUrl;
        this.category = product.category;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}
