package com.softjourn.sj_coin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductDetailsFragment;
import com.softjourn.sj_coin.adapters.FeaturedProductItemsAdapter;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.contratcts.SeeAllContract;
import com.softjourn.sj_coin.events.OnProductBuyClickEvent;
import com.softjourn.sj_coin.events.OnProductDetailsClick;
import com.softjourn.sj_coin.presenters.PurchasePresenter;
import com.softjourn.sj_coin.presenters.SeeAllPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

public class SeeAllActivity extends BaseActivity implements SeeAllContract.View, PurchaseContract.View, Const, Extras, NavigationView.OnNavigationItemSelectedListener {

    private SeeAllContract.Presenter mVendingPresenter;
    private PurchaseContract.Presenter mPurchasePresenter;

    //private LeftSideMenuController mLeftSideMenuController;

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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mVendingPresenter = new SeeAllPresenter(this);
        mPurchasePresenter = new PurchasePresenter(this);

        mCategory = getIntent().getStringExtra(EXTRAS_CATEGORY);
        setTitle(mCategory);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        /*getSupportActionBar().setHomeButtonEnabled(true);*/

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.see_all_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        if (mNavigationView != null) {
            mLeftSideMenuController = new LeftSideMenuController(mNavigationView);

            mLeftSideMenuController.addCategoriesToMenu(mNavigationView.getMenu(), mVendingPresenter.getCategories());
            mNavigationView.setNavigationItemSelectedListener(this);
        }*/
        attachFragment(mCategory);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*int id = item.getItemId();
        mCategory = item.getTitle().toString();
        mLeftSideMenuController.unCheckAllMenuItems(mNavigationView);

        switch (id) {
            case R.id.menu_all_products:
                changeCategory(0, getString(R.string.allItems), item);
                break;
            case R.id.menu_favorites:
                changeCategory(1, getString(R.string.favorites), item);
                break;
            case R.id.menu_last_added:
                changeCategory(2, getString(R.string.lastAdded), item);
                break;
            case R.id.menu_best_sellers:
                changeCategory(3, getString(R.string.bestSellers), item);
                break;
            default:
                changeCategory(mLeftSideMenuController.getItemPosition(mCategory), item.getTitle().toString(), item);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;*/
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (!mCategory.equals(FAVORITES)) {
            menu.findItem(R.id.action_search).setVisible(true);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void navigateToBuyProduct(Product product) {
        onCreateConfirmDialog(product, mPurchasePresenter);
    }

    @Override
    public void showSnackBar() {
        Utils.showSnackBar(findViewById(R.id.rootLayout), getString(R.string.activity_order_processing));
    }

    @Override
    public void logOut() {
        Utils.clearUsersData();
        Navigation.goToLoginActivity(this);
        finish();
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
        mVendingPresenter.onDestroy();
        mPurchasePresenter.onDestroy();
        mVendingPresenter = null;
        mPurchasePresenter = null;
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }*/
        super.onBackPressed();
    }

    /**
     * Method to show correct fragment according to chosen in NavBar Menu
     *
     * @param position position of item in LeftSideMenu
     * @param title    of the Category to be appeared
     * @param item     item of Menu used for checking correct item in LeftSideMenu
     */
    private void changeCategory(int position, String title, MenuItem item) {
        setTitle(title);
        item.setChecked(true);
        mSearch.clearFocus();
        mSearch.onActionViewCollapsed();
        Navigation.navigationOnCategoriesSeeAll(position, SeeAllActivity.this, title);
    }

    private void attachFragment(String stringExtra) {
        switch (stringExtra) {
            case ALL_ITEMS:
                //mNavigationView.getMenu().getItem(0).setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(0, SeeAllActivity.this, ALL_ITEMS);
                break;
            case FAVORITES:
                //mNavigationView.getMenu().getItem(1).setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(1, SeeAllActivity.this, FAVORITES);
                break;
            case BEST_SELLERS:
                //mNavigationView.getMenu().getItem(3).setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(3, SeeAllActivity.this, BEST_SELLERS);
                break;
            case LAST_ADDED:
                //mNavigationView.getMenu().getItem(2).setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(2, SeeAllActivity.this, LAST_ADDED);
                break;
            default:
                //mNavigationView.getMenu().getItem(mLeftSideMenuController.getItemPosition(mCategory)).setChecked(true);
                Navigation.navigationOnCategoriesSeeAll(-1, SeeAllActivity.this, mCategory);
        }
    }

    public void setNavigationItemChecked(String category) {
        //mLeftSideMenuController.setCheckedCategory(category);
    }

    /**
     * Method to disable sorting buttons in case Search is active as Search is handling in Activity class
     * and Buttons are on the Fragment side.
     */
    public void setButtons(Button nameButton, Button priceButton) {
        this.mFragmentsSortNameButton = nameButton;
        this.mFragmentsSortPriceButton = priceButton;
        mFragmentsSortNameButton.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.colorScreenBackground));
        mFragmentsSortPriceButton.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.transparent));
    }

    /**
     * Method to set Category and adapter for correct appearing title and correct
     * checking of Item in NavBar.
     *
     * @param adapter  is object of FeaturedProductItemsAdapter for correct behaviour of filtering
     *                 in Search functionality
     * @param category String with the name of the category
     */
    public void productsList(FeaturedProductItemsAdapter adapter, String category) {
        mCategory = category;
        mAdapter = adapter;
        invalidateOptionsMenu();
    }

    @Subscribe
    public void OnEvent(OnProductDetailsClick event) {
        Bundle args = new Bundle();
        args.putParcelable("PRODUCT_EXTRAS", event.getProduct());

        BottomSheetDialogFragment bottomSheetDialogFragment = new ProductDetailsFragment();
        bottomSheetDialogFragment.setArguments(args);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }

    @Subscribe
    public void OnEvent(OnProductBuyClickEvent event) {
        if (mVendingPresenter.isProductInMachine(event.buyProduct().getId())) {
            navigateToBuyProduct(event.buyProduct());
        } else {
            onCreateErrorDialog(App.getContext().getResources().getString(R.string.product_is_not_available_in_machine));
        }
    }
}
