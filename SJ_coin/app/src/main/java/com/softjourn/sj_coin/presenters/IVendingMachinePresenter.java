package com.softjourn.sj_coin.presenters;

/**
 * Created by Ad1 on 02.08.2016.
 */
public interface IVendingMachinePresenter {
    void callProductsList(String machineID);

    void callMachinesList();

    void callConcreteMachine(String machineID);
}
