package com.softjourn.sj_coin.managers;


import java.util.List;

public interface SortingManager<CustomizedProduct> extends Comparable<CustomizedProduct>{

    List<CustomizedProduct> sortByPrice(List<CustomizedProduct> product, boolean isSortingForward);
    }

