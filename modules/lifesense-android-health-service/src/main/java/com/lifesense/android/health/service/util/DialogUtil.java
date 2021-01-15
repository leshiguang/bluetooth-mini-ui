package com.lifesense.android.health.service.util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.util.dialog.CustomDialog;
import com.lifesense.android.health.service.util.dialog.DialogConfig;
import com.lifesense.android.health.service.util.dialog.DialogUtilImp;

public class DialogUtil {
	public static DialogUtil dialogUtil;
	private PopupWindow mPopupWindow = null;
	private Dialog dialog;
	private View view;
	private ImageView mIvDelete;
	private TextView mTvToConfirm;
	private TextView mTvTips;

	public static DialogUtil getInstance() {
		if (dialogUtil == null) {
			dialogUtil = new DialogUtil();
		}
		return dialogUtil;
	}

	public View getPopView() {
		return view;
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setToConfirmOnClickListener(View.OnClickListener listener) {
		if (mTvToConfirm == null) {
			return;
		}
		mTvToConfirm.setOnClickListener(listener);
	}

	public void setIvDeleteOnClickListener(View.OnClickListener listener) {
		if (mIvDelete == null) {
			return;
		}
		mIvDelete.setOnClickListener(listener);
	}

	public void setTvTipsText(String s) {
		if (mTvTips == null) {
			return;
		}
		mTvTips.setText(s);
	}

	/**
	 *
	 * 捕获对话框显示异常
	 *
	 * @param dialog
	 */
	public static void showDialog(Dialog dialog) {
		if (dialog == null) {
			return;
		}
		try {
			dialog.show();
			isShowDialog=true;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 捕获对话框取消异常
	 * @param dialog
	 */
	public static void dismissDialog(Dialog dialog) {
		if (dialog == null) {
			return;
		}
		try {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {

		}
	}

	private static boolean isShowDialog;

	public boolean isShowDialog() {
		return isShowDialog;
	}


	public void showProcessDialog(Context context) {
		showProcessDialog(context, null, true);
	}

	/**
	 * @param context
	 * @param content
	 * @param isCancelable 设置为false，按返回键不能退出。默认为true。
	 */
	public void showProcessDialog(Context context, String content, boolean isCancelable) {
		dismissProcessDialog();
		if (context == null) {
			return;
		}
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		int w = (int) (dm.widthPixels * 0.5);
		int h = (int) (dm.heightPixels * 0.2);
		//        dialog = new CustomDialog(context, w, h, R.layout.scale_dialog_hint_loading);
		dialog = CustomDialog.createStandardDialog(context, R.layout.scale_dialog_hint_loading);
		dialog.setCancelable(isCancelable);
		TextView loadint_text = dialog.findViewById(R.id.loadint_text);
		if (!TextUtils.isEmpty(content)) {
			loadint_text.setText(content);
		}

		showDialog(dialog);
	}

	/**
	 * @param context
	 * @param isCancelable 设置为false，按返回键不能退出。默认为true。
	 */
	public void showProcessDialog(Context context, boolean isCancelable, int layoutId) {
		dismissProcessDialog();
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		int w = (int) (dm.widthPixels * 0.5);
		int h = (int) (dm.heightPixels * 0.2);
		//        dialog = new CustomDialog(context, w, h, R.layout.scale_dialog_hint_loading);
		dialog = CustomDialog.createStandardDialog(context, layoutId);
		dialog.setCancelable(isCancelable);
		//dialog.show();
		showDialog(dialog);
	}

	public void dismissProcessDialog() {

		if (dialog != null) {
			boolean isDismiss = false;
			if (dialog.isShowing() && dialog.getContext() instanceof AppCompatActivity) { // 3重保护
				AppCompatActivity activity = (AppCompatActivity) dialog.getContext();
				if (!activity.isFinishing()) { // 依附的activity未结束
					dialog.dismiss();
					isDismiss = true;
				}
			}
			if (!isDismiss) {// 如果没经过上述流程，则try catch 处理
				try {
					dialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		dialog = null;
		isShowDialog=false;
	}

	public void dismissDialog() {
		dismissProcessDialog();
	}
	public Dialog showFullscreenDialog(int layoutId, Context context) {
		CustomDialog customDialog = new CustomDialog(context, layoutId,true);
		return customDialog;
	}

	public void showTitleTwoBtnDialog(DialogConfig dialogConfig) {
		dismissDialog();
		dialog = DialogUtilImp.getInstance().showTitleTwoBtnDialog(dialogConfig);
		showDialog(dialog);
	}

}
