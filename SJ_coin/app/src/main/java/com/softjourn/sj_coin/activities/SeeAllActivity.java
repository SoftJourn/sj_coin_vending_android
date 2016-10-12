package com.softjourn.sj_coin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnAddFavoriteEvent;
import com.softjourn.sj_coin.callbacks.OnProductBuyClickEvent;
import com.softjourn.sj_coin.callbacks.OnRemoveFavoriteEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SeeAllActivity extends BaseActivity implements VendingContract.View, Const, Extras, NavigationView.OnNavigationItemSelectedListener {

    private VendingContract.Presenter mPresenter;

    private FeaturedProductItemsAdapter mAdapter;

    private SearchView mSearch;

    private String mCategory;

    private Button mFragmentsSortNameButton;
    private Button mFragmentsSortPriceButton;

    private NavigationView mNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        mPresenter = new VendingPresenter(this);

        mCategory = getIntent().getStringExtra(EXTRAS_CATEGORY);
        setTitle(mCategory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        assert mNavigationView != null;
        addCategoriesToMenu(mNavigationView.getMenu());
        mNavigationView.setNavigationItemSelectedListener(this);

        attachFragment(mCategory);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        mCategory = item.getTitle().toString();
        unCheckAllMenuItems(mNavigationView);

        switch (id) {
            case R.id.allProducts:
                mSearch.clearFocus();
                mSearch.onActionViewCollapsed();
                item.setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(0, SeeAllActivity.this, null);
                setTitle(R.string.allItems);
                break;
            case R.id.favorites:
                mSearch.clearFocus();
                mSearch.onActionViewCollapsed();
                item.setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(1, SeeAllActivity.this, null);
                setTitle(R.string.favorites);
                break;
            case R.id.lastAdded:
                mSearch.clearFocus();
                mSearch.onActionViewCollapsed();
                item.setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(2, SeeAllActivity.this, null);
                setTitle(R.string.lastAdded);
                break;
            case R.id.bestSellers:
                mSearch.clearFocus();
                mSearch.onActionViewCollapsed();
                item.setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(3, SeeAllActivity.this, null);
                setTitle(R.string.bestSellers);
                break;
            default:
                item.setChecked(true);
                mSearch.clearFocus();
                mSearch.onActionViewCollapsed();
                Navigation.navigationOnCategoriesSeeAll(getItemPosition(), SeeAllActivity.this, item.getTitle().toString());
                setTitle(item.getTitle().toString());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.select_machine).setVisible(false);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        mSearch = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearch.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        mSearch.setQueryHint(getString(R.string.search_hint));

        ((EditText) mSearch.findViewById(R.id.search_src_text)).setTextColor(Color.WHITE);
        ((EditText) mSearch.findViewById(R.id.search_src_text)).setHintTextColor(Color.WHITE);

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SeeAllActivity.this.getCurrentFocus().getWindowToken(), 0);
                mSearch.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (TextUtils.isEmpty(query)) {
                    mAdapter.getFilter().filter("");
                } else {
                    mAdapter.getFilter().filter(query);
                }
                mFragmentsSortNameButton.setEnabled(false);
                mFragmentsSortPriceButton.setEnabled(false);

                return true;
            }

        });

        mSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mFragmentsSortNameButton.setEnabled(true);
                mFragmentsSortPriceButton.setEnabled(true);
                return false;
            }
        });

        return true;
    }

    @Override
    public void navigateToBuyProduct(Product product) {
        onCreateDialog(product, mPresenter);
    }

    @Override
    public void navigateToFragments() {

    }

    @Override
    public void setSortedData(List<Product> product) {

    }

    @Override
    public void loadUserBalance() {

    }

    @Override
    public void updateBalanceAmount(String amount) {

    }

    @Override
    public void changeFavoriteIcon() {

    }

    @Override
    public void loadData(List<Product> data) {

    }

    @Override
    public void createContainer(String categoryName) {

    }

    @Override
    public void showMachinesSelector(List<Machines> machines) {

    }

    @Override
    public void loadProductList() {

    }

    @Override
    public void showToastMessage(String message) {
        super.showToast(message);
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void attachFragment(String stringExtra) {
        switch (stringExtra) {

            case ALL_ITEMS:
                mNavigationView.getMenu().getItem(0).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(ALL_ITEMS, 0, 0), TAG_ALL_PRODUCTS_FRAGMENT)
                        .commit();
                break;
            case FAVORITES:
                mNavigationView.getMenu().getItem(1).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(FAVORITES, 0, 0), TAG_FAVORITES_FRAGMENT)
                        .commit();
                break;
            case BEST_SELLERS:
                mNavigationView.getMenu().getItem(3).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(BEST_SELLERS, 0, 0), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                        .commit();
                break;
            case LAST_ADDED:
                mNavigationView.getMenu().getItem(2).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(LAST_ADDED, 0, 0), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                        .commit();
                break;
            default:
                mNavigationView.getMenu().getItem(getItemPosition()).setChecked(true);
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container_for_see_all_products, ProductsListFragment.newInstance(mCategory, 0, 0), mCategory)
                        .commit();
        }
    }

    public void setNavigationItemChecked(String category){
        switch (category){
            case ALL_ITEMS:
                mNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case FAVORITES:
                mNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            default:
                break;
        }

    }

    private void addCategoriesToMenu(Menu menu) {
        List<Categories> categoriesList = RealmController.with(this).getCategories();
        for (Categories currentCategory : categoriesList) {
            menu.add(Preferences.retrieveStringObject(currentCategory.getName().toUpperCase()));
        }

        for (int i = 4; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setCheckable(true);
        }
    }

    private void unCheckAllMenuItems(NavigationView navigationView) {
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

    private int getItemPosition() {
        int position = 0;
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            if (mNavigationView.getMenu().getItem(i).getTitle().equals(mCategory)) {
                position = i;
            }
        }
        return position;
    }

    public void setButtons(Button button, Button button2) {
        this.mFragmentsSortNameButton = button;
        this.mFragmentsSortPriceButton = button2;
    }

    public void productsList(FeaturedProductItemsAdapter adapter) {
        mAdapter = adapter;
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event) {
        navigateToBuyProduct(event.buyProduct());
    }

    @Subscribe
    public void OnEvent(OnAddFavoriteEvent event) {
        mPresenter.addToFavorite(event.addFavorite().getId());
    }

    @Subscribe
    public void OnEvent(OnRemoveFavoriteEvent event) {
        mPresenter.removeFromFavorite(String.valueOf(event.removeFavorite().getId()));
    }
}
