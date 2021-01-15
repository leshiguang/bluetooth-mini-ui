package com.lifesense.jumpaction.action;

/**
 * Created by liuxinyi on 16/12/29.
 */

public abstract class LSAction {

    private String mActionId;
    private LSAction nextAction;

    public LSAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(LSAction nextAction) {
        this.nextAction = nextAction;
    }

//    public LSAction(String actionId) {
//        mActionId = actionId;
//    }

    public abstract String getActionType();

    public final String getActionId() {
        return mActionId;
    }


    public void setActionId(String actionId) {
        this.mActionId = actionId;
    }
}
