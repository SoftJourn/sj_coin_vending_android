package com.softjourn.sj_coin.utils;

import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.products.Categories;

import java.util.List;

/**
 * Created by omartynets on 28.11.2016.
 * to handle work with items on the leftSide menu
 * in separate class and not in the activity class
 */

public class LeftSideMenuController {

    private NavigationView mNavigationView;

    public LeftSideMenuController(NavigationView navigationView) {
        this.mNavigationView = navigationView;
    }

    /**
     * Method is using to populate Left Side menu with needed items
     *
     * @param menu           to be worked with
     * @param categoriesList List of categories derived from server
     */
    public void addCategoriesToMenu(Menu menu, List<Categories> categoriesList) {

        MenuItem menuItem = menu.findItem(R.id.categories_subheader);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.clear();

        for (Categories currentCategory : categoriesList) {
            subMenu.add(Preferences.retrieveStringObject(currentCategory.getName().toUpperCase()));
        }

        for (int i = 4; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setCheckable(true);
        }
    }

    /**
     * Makes all items in NavBar not checked
     * before setting chosen item as checked
     */
    public void unCheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    /**
     * Is using to find item of the category in categories list on the LeftSideMenu.
     * Category could be derived from server on any position of the list so there is no
     * known position of exact category
     *
     * @param category name of the category to be found in the LeftSideMenu
     * @return position of item in Left Side Menu from given name of the category
     */
    public int getItemPosition(String category) {
        int position = 0;
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            if (mNavigationView.getMenu().getItem(i).getTitle().equals(category)) {
                position = i;
            }
        }
        return position;
    }

    public void uncheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    /**
     * Method to set checked correct category in LeftSide Menu if it was chosen from MainMenu
     *
     * @param category category name derived from Fragment
     */
    public void setCheckedCategory(String category) {
        switch (category) {
            case Const.ALL_ITEMS:
                mNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case Const.FAVORITES:
                mNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            default:
                break;
        }
    }
}
