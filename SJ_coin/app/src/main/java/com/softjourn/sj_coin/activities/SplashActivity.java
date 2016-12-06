package com.softjourn.sj_coin.activities;

import android.text.TextUtils;

import com.daimajia.androidanimations.library.Techniques;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash implements Const {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.colorScreenBackground);
        configSplash.setAnimCircularRevealDuration(100); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.vending_machine_png); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.transparent); //path object filling color

        //Customize Title
        configSplash.setTitleSplash(""); // Empty string to not appear application title
        configSplash.setTitleTextColor(R.color.colorBlue);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(100);
    }

    @Override
    public void animationsFinished() {
        if (!NetworkUtils.isNetworkEnabled()) {
            Navigation.goToNoInternetScreen(this);
            finish();
        } else {
            if (TextUtils.isEmpty(Preferences.retrieveStringObject(ACCESS_TOKEN))
                    && TextUtils.isEmpty(Preferences.retrieveStringObject(REFRESH_TOKEN))) {
                Navigation.goToLoginActivity(this);
                finish();
            } else {
                Navigation.goToVendingActivity(this);
                finish();
            }
        }
    }
}
