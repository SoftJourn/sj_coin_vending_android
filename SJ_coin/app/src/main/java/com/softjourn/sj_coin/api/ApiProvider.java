package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Machines.Machine;
import com.softjourn.sj_coin.model.Session;

import retrofit2.Callback;

/**
 * Created by Ad1 on 02.08.2016.
 */
public interface ApiProvider {
    void makeLoginRequest(String email, String password, String type, Callback<Session> callback);

    void getSelectedMachine(String selectedMachine, Callback<Machine> callback);
}
