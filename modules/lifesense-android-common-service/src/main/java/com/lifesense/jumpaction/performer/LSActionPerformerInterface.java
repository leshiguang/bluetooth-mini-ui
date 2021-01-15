package com.lifesense.jumpaction.performer;


import com.lifesense.jumpaction.action.LSAction;

/**
 * Created by liuxinyi on 16/12/29.
 */

public interface LSActionPerformerInterface {
    /**
     * 返回 类型
     *
     * @return
     */
    public String getActionType();

    public void performerAction(LSAction lsAction);

}
