package com.lifesense.jumpaction.performer.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lifesense.jumpaction.action.ActivityAction;
import com.lifesense.jumpaction.action.ActivityActionForResult;
import com.lifesense.jumpaction.action.LSAction;
import com.lifesense.jumpaction.performer.LSActionPerformerInterface;


/**
 * 跳转Activity任务的处理类
 * Created by liuxinyi on 16/12/29.
 */

public class ActivityPerformer implements ActivityPerformerInterface, LSActionPerformerInterface {

    @Override
    public void startActivity(Context context, String action, Bundle bundle, int flags) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(context.getPackageName());
        fillIntent(intent, flags, bundle);
        startActivity(context, intent);
    }

    private void startActivity(Context context, Intent intent) {

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startActivityForResult(Activity context, Intent intent, int requestCode) {

        try {
            context.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Intent fillIntent(Intent intent, int flags, Bundle bundle) {
        //这里执行跳转
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags != 0) {
            intent.addFlags(flags);
        }
        return intent;
    }

    @Override
    public void startActivityForResult(Activity context, String action, Bundle bundle, int flags, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setPackage(context.getPackageName());
        fillIntent(intent, flags, bundle);
        //这里执行跳转
        startActivityForResult(context, intent, requestCode);
    }

    @Override
    public void startActivity(Context context, Class<?> actionClass, Bundle bundle, int flags) {
        Intent intent = new Intent();
        intent.setClass(context, actionClass);
        intent.setPackage(context.getPackageName());
        fillIntent(intent, flags, bundle);
        startActivity(context, intent);
    }

    @Override
    public void startActivityForResult(Activity context, Class<?> actionClass, Bundle bundle, int flags, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, actionClass);
        fillIntent(intent, flags, bundle);
        //这里执行跳转
        startActivityForResult(context, intent, requestCode);
    }


    @Override
    public String getActionType() {
        return ActivityPerformer.class.getName();
    }


    @Override
    public void performerAction(LSAction lsAction) {
        if (!(lsAction instanceof ActivityAction)) {
            return;
        }
        ActivityAction activityAction = (ActivityAction) lsAction;
        if (activityAction.getType() == ActivityAction.TYPE_ACTION_ID) {
            if (lsAction instanceof ActivityActionForResult) {
                ActivityActionForResult activityActionForResult = (ActivityActionForResult) lsAction;
                startActivityForResult((Activity) activityActionForResult.getContext(),
                        activityActionForResult.getActionId(), activityActionForResult.getBundle(), activityActionForResult.getFlags(), activityActionForResult.getRequestCode());
            } else {
                startActivity(activityAction.getContext(), activityAction.getActionId(), activityAction.getBundle(), activityAction.getFlags());
            }
        } else if (activityAction.getType() == ActivityAction.TYPE_CLASS) {
            if (lsAction instanceof ActivityActionForResult) {
                ActivityActionForResult activityActionForResult = (ActivityActionForResult) lsAction;
                startActivityForResult((Activity) activityActionForResult.getContext(),
                        activityActionForResult.getActivityClass(), activityActionForResult.getBundle(), activityActionForResult.getFlags(), activityActionForResult.getRequestCode());
            } else {
                startActivity(activityAction.getContext(), activityAction.getActivityClass(), activityAction.getBundle(), activityAction.getFlags());

            }
        }


    }


}
