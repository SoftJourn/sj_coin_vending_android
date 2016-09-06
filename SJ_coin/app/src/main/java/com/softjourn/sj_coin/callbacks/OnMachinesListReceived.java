package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.machines.Machines;

import java.util.List;

public class OnMachinesListReceived {
    private List<Machines> mMachines;

    public OnMachinesListReceived (List<Machines> machines) {
        this.mMachines = machines;
    }

    public List<Machines> getMachinesList(){
        return mMachines;
    }
}
