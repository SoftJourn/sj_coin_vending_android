package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.machines.Machines;

import java.util.List;

/**
 * Created by Ad1 on 03.08.2016.
 */
public class OnMachinesListReceived {
    private List<Machines> mMachines;

    public OnMachinesListReceived (List<Machines> machines) {
        this.mMachines = machines;
    }

    public List<Machines> getMachinesList(){
        return mMachines;
    }
}
