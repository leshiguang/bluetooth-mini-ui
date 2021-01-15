package com.lifesense.jumpaction;


import com.lifesense.jumpaction.action.LSAction;
import com.lifesense.jumpaction.performer.LSActionPerformerInterface;

/**
 * Created by liuxinyi on 16/12/29.
 */

public interface LSActionPerformerManagerInterface {
    void registerPerformer(LSActionPerformerInterface actionPerformer);

    void unregisterPerformer(LSActionPerformerInterface actionPerformer);

    LSActionPerformerInterface getPerformer(String actionType);

    void sendAction(LSAction lsAction);
}
