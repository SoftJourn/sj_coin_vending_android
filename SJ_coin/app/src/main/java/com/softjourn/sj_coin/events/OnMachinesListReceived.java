package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.machines.Machines;

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
