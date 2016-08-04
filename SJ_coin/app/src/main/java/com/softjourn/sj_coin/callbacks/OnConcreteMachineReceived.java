package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.machines.Machines;

/**
 * Created by Ad1 on 04.08.2016.
 */
public class OnConcreteMachineReceived {
    private Machines mMachines;

    public OnConcreteMachineReceived (Machines machines) {
        this.mMachines = machines;
    }

    public Machines getСoncreteMachines(){
        return mMachines;
    }
}
