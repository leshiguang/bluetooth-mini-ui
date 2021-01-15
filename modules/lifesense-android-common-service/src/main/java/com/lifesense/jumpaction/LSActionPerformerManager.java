package com.lifesense.jumpaction;

import com.lifesense.jumpaction.action.LSAction;
import com.lifesense.jumpaction.performer.LSActionPerformerInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxinyi on 16/12/29.
 */

public final class LSActionPerformerManager implements LSActionPerformerManagerInterface {
    private Map<String, LSActionPerformerInterface> performerInterfaceMap = new HashMap<>();
    private static volatile LSActionPerformerManager lsActionPerformerManager;

    private LSActionPerformerManager() {
    }

    public static LSActionPerformerManagerInterface getInstance() {
        if (lsActionPerformerManager == null) {
            synchronized (LSActionPerformerManager.class) {
                if (lsActionPerformerManager == null) {
                    lsActionPerformerManager = new LSActionPerformerManager();
                }
            }
        }
        return lsActionPerformerManager;
    }

    @Override
    public void registerPerformer(LSActionPerformerInterface actionPerformer) {
        performerInterfaceMap.put(actionPerformer.getActionType(), actionPerformer);
    }

    @Override
    public void unregisterPerformer(LSActionPerformerInterface actionPerformer) {
        performerInterfaceMap.remove(actionPerformer.getActionType());
    }

    @Override
    public LSActionPerformerInterface getPerformer(String actionType) {

        return performerInterfaceMap.get(actionType);
    }

    @Override
    public void sendAction(LSAction lsAction) {
        LSActionPerformerInterface lsActionPerformerInterface = performerInterfaceMap.get(lsAction.getActionType());
        if (lsActionPerformerInterface != null) {
            dispatchAction(lsAction);
        }
    }

    private void dispatchAction(LSAction lsAction) {
        LSActionPerformerInterface lsActionPerformerInterface = performerInterfaceMap.get(lsAction.getActionType());
        if (lsActionPerformerInterface != null) {
            lsActionPerformerInterface.performerAction(lsAction);
            if (lsAction.getNextAction() != null && lsAction.getNextAction() != lsAction) {
                dispatchAction(lsAction.getNextAction());
            }
        }
    }
}
