package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.presenters.BalancePresenter;
import com.softjourn.sj_coin.presenters.IBalancePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements Constants {

    @Bind(R.id.profile_user_photo)
    ImageView mUserPhoto;

    @Bind(R.id.profile_user_name)
    TextView mUserName;

    @Bind(R.id.profile_amount_available)
    TextView mUserBalance;

    private IBalancePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (!isInternetAvailable()) {
            onNoInternetAvailable();
            finish();
        } else {

            ButterKnife.bind(this);

            try {
                mUserName.setText(getUserName());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(EXPIRATION_DATE))) {
                callRefreshToken();
            } else {
                callBalance();
            }
        }
    }

    private void callBalance() {
        if (!isInternetAvailable()) {
            onNoInternetAvailable();
        } else {
            mPresenter = new BalancePresenter();
            mPresenter.callBalance();
            ProgressDialogUtils.showDialog(this, getString(R.string.progress_loading));
        }
    }

    private String getUserName() throws UnsupportedEncodingException {

        String source = Preferences.retrieveStringObject(ACCESS_TOKEN).replaceFirst("[a-zA-Z,0-9]*\\.", "").replaceFirst("\\.[a-zA-Z,0-9]*", "");
        byte[] data = Base64.decode(source, Base64.DEFAULT);
        String decodedString = new String(data, "UTF-8");

        //Work with decoded String
        int tempStart = decodedString.indexOf(getString(R.string.activity_profile_username_key));
        String startOfUserName = decodedString.substring(tempStart + 12);
        int endOfUserName = startOfUserName.indexOf("\"");
        return startOfUserName.substring(0, endOfUserName);
    }

    @Subscribe
    public void OnEvent(OnBalanceReceivedEvent event) {
        mUserBalance.setText(event.getBalance().getAmount());
        onCallSuccess();
    }

    @Subscribe
    public void OnEvent(OnCallEvent event) {
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event){
        if (event.isSuccess()) {
            onCallSuccess();
            callBalance();
        } else {
            onCallFailed();
        }
    }
}
