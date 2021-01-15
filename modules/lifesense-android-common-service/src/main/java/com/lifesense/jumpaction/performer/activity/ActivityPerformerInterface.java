package com.lifesense.jumpaction.performer.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by liuxinyi on 16/12/29.
 */

public interface ActivityPerformerInterface {
    void startActivity(Context context, String action, Bundle bundle, int flags);

    void startActivityForResult(Activity context, String action, Bundle bundle, int flags, int requestCode);
    void startActivity(Context context, Class<?> actionClass, Bundle bundle, int flags);
    void startActivityForResult(Activity context,  Class<?> actionClass, Bundle bundle, int flags, int requestCode);


}
