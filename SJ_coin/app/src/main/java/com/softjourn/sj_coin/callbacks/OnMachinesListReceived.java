package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.api_models.machines.Machines;

import java.util.List;

public class OnMachinesListReceived {

    private final List<Machines> mMachines;

    public OnMachinesListReceived (List<Machines> machines) {
        this.mMachines = machines;
    }

    public List<Machines> getMachinesList(){
        return mMachines;
    }
}
