package com.softjourn.sj_coin.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.ProductsListFragment;
import com.softjourn.sj_coin.adapters.SelectMachineListAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnFavoritesListReceived;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.contratcts.PurchaseContract;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.presenters.PurchasePresenter;
import com.softjourn.sj_coin.presenters.VendingPresenter;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        VendingContract.View, PurchaseContract.View, Const {

    private VendingContract.Presenter mVendingPresenter;
    private PurchaseContract.Presenter mPurchasePresenter;

    private int viewCounter = 0;

    @Bind(R.id.balance)
    TextView mBalance;

    @Bind(R.id.textViewFavoritesSeeAll)
    TextView seeAllFavorites;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVendingPresenter = new VendingPresenter(this);
        mPurchasePresenter = new PurchasePresenter(this);

        ButterKnife.bind(this);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        makeActionOverflowMenuShown();

        mVendingPresenter.isMachineSet();
    }

    @OnClick({R.id.textViewLastAddedSeeAll, R.id.textViewBestSellersSeeAll, R.id.textViewFavoritesSeeAll})
    public void seeAll(View v) {
        switch (v.getId()) {
            case R.id.textViewFavoritesSeeAll:
                Navigation.goToSeeAllActivity(this, FAVORITES);
                break;
            case R.id.textViewLastAddedSeeAll:
                Navigation.goToSeeAllActivity(this, LAST_ADDED);
                break;
            case R.id.textViewBestSellersSeeAll:
                Navigation.goToSeeAllActivity(this, BEST_SELLERS);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.select_machine:
                mVendingPresenter.getMachinesList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        if (TextUtils.isEmpty(Preferences.retrieveStringObject(SELECTED_MACHINE_ID))) {
            showToastMessage(getString(R.string.machine_not_selected_toast));
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            removeContainers();
            loadProductList();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Override
    public void navigateToBuyProduct(Product product) {
        onCreateConfirmDialog(product, mPurchasePresenter);
    }

    @Override
    public void navigateToFragments() {

        getFragmentManager().beginTransaction()
                .replace(R.id.container_fragment_products_list_favorites, ProductsListFragment.newInstance(FAVORITES, 0, 0), TAG_FAVORITES_FRAGMENT)
                .replace(R.id.container_fragment_products_list_new_products, ProductsListFragment.newInstance(LAST_ADDED, 0, 0), TAG_PRODUCTS_LAST_ADDED_FRAGMENT)
                .replace(R.id.container_fragment_products_list_best_sellers, ProductsListFragment.newInstance(BEST_SELLERS, 0, 0), TAG_PRODUCTS_BEST_SELLERS_FRAGMENT)
                .commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void loadUserBalance() {
        mVendingPresenter.getBalance();
    }


    @Override
    public void showToastMessage(String message) {
        super.showToast(message);
    }

    @Override
    public void updateBalanceAmount(String balance) {
        mBalance.setVisibility(View.VISIBLE);
        mBalance.setText(String.format(getString(R.string.your_balance_is), balance));
    }

    @Override
    public void createContainer(final String categoryName) {

        viewCounter++;

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        LinearLayout ll;

        ll = (LinearLayout) getLayoutInflater().inflate(R.layout.category_header_layout, null);
        assert mainLayout != null;
        mainLayout.addView(ll);

        LinearLayout llHeader = (LinearLayout) findViewById(R.id.dummyHeaderID);
        assert llHeader != null;
        llHeader.setId(View.generateViewId());

        TextView tvCategoryName = (TextView) findViewById(R.id.categoryName);
        assert tvCategoryName != null;
        tvCategoryName.setId(View.generateViewId());
        tvCategoryName.setText(categoryName);

        TextView tvSeeAll = (TextView) findViewById(R.id.dummySeeAllID);
        assert tvSeeAll != null;
        tvSeeAll.setId(View.generateViewId());

        LinearLayout llContainer = (LinearLayout) findViewById(R.id.container_dummyID);
        assert llContainer != null;
        llContainer.setId(View.generateViewId());

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.goToSeeAllActivity(VendingActivity.this, categoryName);
            }
        });

        attachFragment(categoryName, llHeader.getId(), llContainer.getId(), tvSeeAll.getId());
    }

    @Override
    public void getMachinesList() {
        mVendingPresenter.getMachinesList();
    }

    @Override
    public void showSnackBar(String message) {
        Utils.showSnackBar(findViewById(R.id.mainLayout), message);
    }

    @Override
    public void showMachinesSelector(final List<Machines> machines) {
        List<String> names = new ArrayList<>();
        for (Machines machine : machines) {
            names.add(machine.getName());
        }

        final Dialog dialog = new Dialog(this);
        if (!dialog.isShowing()) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_select_machine);
            ListView machinesList = (ListView) dialog.findViewById(R.id.lv);
            final SelectMachineListAdapter adapter = new SelectMachineListAdapter(this, android.R.layout.simple_list_item_1, names);
            machinesList.setAdapter(adapter);
            dialog.getWindow().getAttributes().windowAnimations = R.style.MachinesDialogAnimation;
            dialog.show();

            machinesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (Machines machine : machines) {
                        if (adapter.getItem(position).equals(machine.getName())) {
                            Preferences.storeObject(SELECTED_MACHINE_ID, String.valueOf(machine.getId()));
                            Preferences.storeObject(SELECTED_MACHINE_NAME, machine.getName());
                            break;
                        }
                    }
                    showProgress(getString(R.string.progress_loading));
                    loadProductList();
                    dialog.dismiss();
                }
            });
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (TextUtils.isEmpty(Preferences.retrieveStringObject(SELECTED_MACHINE_ID))) {
                        showToastMessage(getString(R.string.machine_not_selected_toast));
                    }
                    hideProgress();
                }
            });
        }
    }

    @Override
    public void loadProductList() {
        removeContainers();
        RealmController.with(this).clearAll();
        mVendingPresenter.getFeaturedProductsList(Preferences.retrieveStringObject(SELECTED_MACHINE_ID));
        loadUserBalance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVendingPresenter.onDestroy();
        mPurchasePresenter.onDestroy();
        mVendingPresenter = null;
        mPurchasePresenter = null;
    }

    private void attachFragment(String categoryName, int headerID, int containerID, int seeAllID) {
        getFragmentManager().beginTransaction().replace(containerID, ProductsListFragment.newInstance(categoryName, headerID, containerID),
                Preferences.retrieveStringObject(categoryName.toUpperCase())).commit();
    }

    public void hideContainer(int headers, int fragmentContainerId) {
        View view = findViewById(headers);
        View fragmentContainer = findViewById(fragmentContainerId);
        assert fragmentContainer != null;
        fragmentContainer.setVisibility(View.GONE);
        assert view != null;
        view.setVisibility(View.GONE);
    }

    public void showContainer(int headers, int fragmentContainerId) {
        View view = findViewById(headers);
        View fragmentContainer = findViewById(fragmentContainerId);

        assert view != null;
        view.setVisibility(View.VISIBLE);
        assert fragmentContainer != null;
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    private void removeContainers() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        for (int i = 0; i < viewCounter; i++) {
            assert layout != null;
            layout.removeView(findViewById(R.id.categoryLayout));
        }
    }


    //Method for showing overflow menu in actionbar
    //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu by default

    private void makeActionOverflowMenuShown() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void OnEvent(OnFeaturedProductsListReceived event) {
        removeContainers();
        mVendingPresenter.getFavoritesList();
    }

    @Subscribe
    public void OnEvent(OnFavoritesListReceived event) {
        mVendingPresenter.getCategoriesFromDB();
        setTitle(Preferences.retrieveStringObject(SELECTED_MACHINE_NAME));
        navigateToFragments();
    }

    @Subscribe
    public void OnEvent(OnMachinesListReceived event) {
        if (event.getMachinesList().size() == 1) {
            if (TextUtils.isEmpty(Preferences.retrieveStringObject(SELECTED_MACHINE_ID))) {
                Utils.storeConcreteMachineInfo(event.getMachinesList().get(0));
                loadProductList();
            } else {
                showMachinesSelector(event.getMachinesList());
            }
        } else if (event.getMachinesList().size() > 1) {
            showMachinesSelector(event.getMachinesList());
        }
    }
}

