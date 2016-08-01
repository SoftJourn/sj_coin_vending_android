package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.ApiClient;
import com.softjourn.sj_coin.api.ApiInterface;
import com.softjourn.sj_coin.model.Machines.Machine;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ServerErrors;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendingActivity extends AppCompatActivity implements Constants{

    @Bind(R.id.btnMachine)
    Button btnMachine;

    @Bind(R.id.machines_spinner)
    Spinner spinnerMachine;
    String mSelectedMachine;

    @OnClick(R.id.btnMachine)
    public void getMachine(){
        makeRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (TextUtils.isEmpty(Preferences.retrieveObject(ACCESS_TOKEN))){
            Navigation.goToLoginActivity(this);
        }

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.machines,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMachine.setAdapter(adapter);

        spinnerMachine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedMachine = adapterView.getItemAtPosition(i).toString();
                Log.d("Tag",mSelectedMachine);
                btnMachine.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                btnMachine.setEnabled(false);
            }
        });

    }

    private void makeRequest() {
        ApiInterface apiInterface = ApiClient.getClient(VENDING,URL_VENDING_SERVICE).create(ApiInterface.class);
        Call<Machine> call = apiInterface.getMachine(mSelectedMachine);
        call.enqueue(new Callback<Machine>() {
            @Override
            public void onResponse(Call<Machine> call, Response<Machine> response) {
                if(!response.isSuccessful()){
                    ServerErrors.showErrorToast(VendingActivity.this,response.code());
                    Log.d("Tag", (String.valueOf(response.code())));
                } else {
                    Log.d("Tag",response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Machine> call, Throwable t) {
                Log.d("TagActivity",t.toString());
            }
        });

    }

}
