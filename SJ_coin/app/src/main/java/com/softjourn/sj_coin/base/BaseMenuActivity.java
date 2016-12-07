package com.softjourn.sj_coin.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

import butterknife.BindView;

/**
 * Created by omartynets on 01.12.2016.
 */

public abstract class BaseMenuActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    public DrawerLayout mMenuLayout;

    @BindView(R.id.left_side_menu)
    public NavigationView mMenuView;

    private ActionBarDrawerToggle mMenuToggle;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (mMenuLayout == null) {
            throw new IllegalStateException("Activity must have DrawerLayout view");
        }

        if (mMenuView == null) {
            throw new IllegalStateException("Activity must have view with left_side_menu id");
        }

        initActionBarToggle();
        initNavigationDrawer();
    }

    private void initActionBarToggle() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_base);
        setSupportActionBar(toolbar);

        mMenuToggle = new ActionBarDrawerToggle(
                this, mMenuLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mMenuLayout.addDrawerListener(mMenuToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mMenuToggle.syncState();

    }

    private void initNavigationDrawer() {
        mMenuView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return handleNavigation(item);
            }
        });

        mMenuLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                setUpNavigationViewContent();
            }
        });

        View headerView = mMenuView.getHeaderView(0);
        headerView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDivider));
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenuLayout.closeDrawer(GravityCompat.START);
                Navigation.goToProfileActivity(BaseMenuActivity.this);
            }
        });

        ImageView iconView = (ImageView) headerView.findViewById(R.id.menu_user_icon);
        iconView.setImageResource(R.drawable.logo);

        TextView userBalanceView = (TextView) headerView.findViewById(R.id.user_balance);
        if (Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY) != null) {
            userBalanceView.setText(Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY));
        } else {
            userBalanceView.setText("");
        }

        TextView usernameView = (TextView) headerView.findViewById(R.id.menu_user_name);
        if (Preferences.retrieveStringObject(USER_NAME_PREFERENCES_KEY) != null) {
            usernameView.setText(Preferences.retrieveStringObject(USER_NAME_PREFERENCES_KEY));
        } else {
            usernameView.setText(R.string.menu_default_username);
        }
        setUpNavigationViewContent();
    }

    private boolean handleNavigation(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_all_products:
                mMenuLayout.closeDrawer(GravityCompat.START);
                Navigation.goToSeeAllActivity(this, ALL_ITEMS);
                break;
            case R.id.menu_favorites:
                mMenuLayout.closeDrawer(GravityCompat.START);
                Navigation.goToSeeAllActivity(this, FAVORITES);
                break;
            case R.id.menu_last_added:
                mMenuLayout.closeDrawer(GravityCompat.START);
                Navigation.goToSeeAllActivity(this, LAST_ADDED);
                break;
            case R.id.menu_best_sellers:
                mMenuLayout.closeDrawer(GravityCompat.START);
                item.setChecked(false);
                Navigation.goToSeeAllActivity(this, BEST_SELLERS);
                break;
            case R.id.menu_logout_item:
                logOut(item);
                mMenuLayout.closeDrawer(GravityCompat.START);
                break;
            default:
                mMenuView.setCheckedItem(item.getItemId());
                onCategorySelected(item);
                mMenuLayout.closeDrawer(mMenuView, true);
                break;
        }
        return true;
    }

    public abstract void logOut(@NonNull MenuItem item);

    public abstract void onCategorySelected(@NonNull MenuItem item);

    // TODO: add updating menu instead of fully clearing it
    public abstract void setUpNavigationViewContent();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mMenuToggle != null) {
            mMenuToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mMenuToggle != null && mMenuToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    protected Menu getMenu() {
        return mMenuView.getMenu();
    }

    @Override
    public void onBackPressed() {
        if (mMenuLayout.isDrawerOpen(GravityCompat.START)) {
            mMenuLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
