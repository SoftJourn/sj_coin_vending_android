package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.Machines.Machine;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class OnVendingMachineReceived {

    private Machine mMachine;

    public OnVendingMachineReceived(Machine machine) {
        this.mMachine = machine;
    }

    public Machine getMachine(){
        return mMachine;
    }
}
