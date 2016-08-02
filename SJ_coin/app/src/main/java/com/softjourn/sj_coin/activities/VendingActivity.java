package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.adapters.MachineItemsAdapter;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnVendingMachineReceived;
import com.softjourn.sj_coin.model.Machines.Field;
import com.softjourn.sj_coin.model.Machines.Machine;
import com.softjourn.sj_coin.presenters.IVendingMachinePresenter;
import com.softjourn.sj_coin.presenters.VendingMachinePresenter;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VendingActivity extends BaseActivity implements Constants {

    Machine mMachine;

    String mSelectedMachine;
    @Bind(R.id.btnMachine)
    Button btnMachine;
    @Bind(R.id.machines_spinner)
    Spinner spinnerMachine;
    @Bind(R.id.machine_items_recycler_view)
    RecyclerView machineItems;
    private IVendingMachinePresenter mPresenter;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @OnClick(R.id.btnMachine)
    public void getMachine() {
        makeRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        machineItems.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        machineItems.setLayoutManager(mLayoutManager);

/*        mAdapter = new MachineItemsAdapter(mMield);
        machineItems.setAdapter(mAdapter);*/

        if (TextUtils.isEmpty(Preferences.retrieveObject(ACCESS_TOKEN))) {
            Navigation.goToLoginActivity(this);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.machines, android.R.layout.simple_spinner_item);

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

    }

    private void makeRequest() {
        mPresenter = new VendingMachinePresenter(mSelectedMachine);
        mPresenter.callVendingMachine();
        ProgressDialogUtils.showDialog(this, "Loading");
    }

    private void setFields() {
        List<Field> field;
        for (int i = 0; i < mMachine.getRows().size() - 1; i++) {
            field = mMachine.getRows().get(i).getFields();
            for (int j = 0; j < field.size() - 1; j++) {
                mAdapter = new MachineItemsAdapter(field.get(j));
                machineItems.setAdapter(mAdapter);
            }
        }
    }

    public void onCallSuccess() {
        ProgressDialogUtils.dismiss();
        setFields();
    }

    public void onCallFailed() {
        ProgressDialogUtils.dismiss();
    }

    @Subscribe
    public void OnEvent(OnVendingMachineReceived event) {
        mMachine = event.getMachine();
    }

    @Subscribe
    public void OnEvent(OnLogin event) {
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }

}
