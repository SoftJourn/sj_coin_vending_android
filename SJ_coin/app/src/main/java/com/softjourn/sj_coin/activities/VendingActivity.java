package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnConcreteMachineReceived;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements Constants {

    public String mSelectedMachine;
    List<Machines> mMachines;
    ArrayAdapter<String> adapter;
    @Bind(R.id.btnMachine)
    Button btnMachine;
    @Bind(R.id.machines_spinner)
    Spinner spinnerMachine;

    private IVendingMachinePresenter mPresenter;

    @OnClick(R.id.btnMachine)
    public void getProducts() {
        callProductsList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseActivity.mActivity = this;

        ButterKnife.bind(this);

        if (TextUtils.isEmpty(Preferences.retrieveStringObject(ACCESS_TOKEN))) {
            Navigation.goToLoginActivity(this);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMachine.setAdapter(adapter);

        spinnerMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedMachine = adapterView.getItemAtPosition(i).toString();
                Log.d("Tag", mSelectedMachine);
                btnMachine.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                btnMachine.setEnabled(false);
            }
        });
        callMachinesList();

    }

    private void callMachinesList() {
        mPresenter = new VendingMachinePresenter();
        mPresenter.callMachinesList();
        ProgressDialogUtils.showDialog(this, "Loading");
    }

    private void callProductsList() {
        Navigation.goToListView(this);
        mPresenter = new VendingMachinePresenter();
        mPresenter.callConcreteMachine(mSelectedMachine);
    }

    private void updateSpinnerData() {
        for (int i = 0; i < mMachines.size(); i++) {
            adapter.add(mMachines.get(i).getId().toString());
        }
        adapter.notifyDataSetChanged();
        spinnerMachine.setSelection(0);
    }

    @Subscribe
    public void OnEvent(OnLogin event) {
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }

    @Subscribe
    public void OnEvent(OnMachinesListReceived event) {
        onCallSuccess();
        mMachines = event.getMachinesList();
        updateSpinnerData();
    }

    @Subscribe
    public void OnEvent(OnConcreteMachineReceived event) {
        Preferences.storeObject(SELECTED_MACHINE_ROWS,event.getСoncreteMachines().getSize().getRows());
        Preferences.storeObject(SELECTED_MACHINE_COLUMNS,event.getСoncreteMachines().getSize().getColumns());
        Preferences.storeObject(SELECTED_MACHINE_ID,event.getСoncreteMachines().getId());
        Preferences.storeObject(SELECTED_MACHINE_NAME,event.getСoncreteMachines().getName());
    }

}
