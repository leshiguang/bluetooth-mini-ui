package com.lifesense.android.health.service.util.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lifesense.android.health.service.R;
import com.lifesense.android.health.service.util.DialogUtil;
import com.lifesense.android.health.service.util.UIUtil;

/**
 * 这里对接UI样式，使用新的对话框弹出，跟之前旧的区分开来
 *
 * @author Sinyi.liu
 * @date 2017/9/26 10:20
 * @describe:
 */
public class DialogUtilImp {

	private static volatile DialogUtilImp singleton = null;

	private DialogUtilImp() {
	}

	public static DialogUtilImp getInstance() {
		if (singleton == null) {
			synchronized (DialogUtilImp.class) {
				if (singleton == null) {
					singleton = new DialogUtilImp();
				}
			}
		}
		return singleton;
	}


	public CustomDialog showTitleTwoBtnDialog(DialogConfig dialogConfig) {
		return showTitleDialog(dialogConfig);
	}
	private CustomDialog showNoTitleOneDialog(DialogConfig dialogConfig) {
		CustomDialog dialog = CustomDialog.createStandardDialog(dialogConfig.getContext(),
				R.layout.scale_dialog_common_no_title_onebtn);
		TextView tv_content = dialog.findViewById(R.id.tvContent);
		TextView tv_confirm = dialog.findViewById(R.id.tvConfirm);
		tv_content.setText(dialogConfig.getContent());
		tv_content.setGravity(dialogConfig.getGravity());
		if (!TextUtils.isEmpty(dialogConfig.getConfirmBtn())) {
			tv_confirm.setText(dialogConfig.getConfirmBtn());
		}
		tv_confirm.setOnClickListener(getClickListener(dialogConfig.getConfirmClickListener()));
		dialog.setCancelable(dialogConfig.isCancelable());
		return dialog;
	}


	private CustomDialog showNoTitleDialog(DialogConfig dialogConfig) {

		CustomDialog dialog = CustomDialog.createStandardDialog(dialogConfig.getContext(),
				R.layout.scale_dialog_common_no_title);
		TextView tv_content = dialog.findViewById(R.id.tvContent);
		TextView tv_confirm = dialog.findViewById(R.id.tvConfirm);
		tv_content.setText(dialogConfig.getContent());
		if (!TextUtils.isEmpty(dialogConfig.getConfirmBtn())) {
			tv_confirm.setText(dialogConfig.getConfirmBtn());
		}
		tv_confirm.setOnClickListener(getClickListener(dialogConfig.getConfirmClickListener()));
		TextView tvCancel = dialog.findViewById(R.id.tvCancel);
		View lineDivide = dialog.findViewById(R.id.lineDivide);
		tvCancel.setVisibility(View.VISIBLE);
		lineDivide.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(dialogConfig.getCancelBtn())) {
			tvCancel.setText(dialogConfig.getCancelBtn());
		}
		tvCancel.setOnClickListener(getClickListener(dialogConfig.getCancelClickListener()));

		dialog.setCancelable(dialogConfig.isCancelable());
		return dialog;
	}

	private CustomDialog showTitleDialog(DialogConfig dialogConfig) {
		if (TextUtils.isEmpty(dialogConfig.getTitle())) {
			return showNoTitleDialog(dialogConfig);
		}

		CustomDialog dialog = CustomDialog.createStandardDialog(dialogConfig.getContext(),
				R.layout.scale_dialog_common_title);
		final TextView tv_content = dialog.findViewById(R.id.tvContent);
		TextView tv_confirm = dialog.findViewById(R.id.tvConfirm);
		tv_content.setText(dialogConfig.getContent());
		if (!TextUtils.isEmpty(dialogConfig.getConfirmBtn())) {
			tv_confirm.setText(dialogConfig.getConfirmBtn());
		}
		tv_confirm.setOnClickListener(getClickListener(dialogConfig.getConfirmClickListener()));
		TextView tvCancel = dialog.findViewById(R.id.tvCancel);
		View lineDivide = dialog.findViewById(R.id.lineDivide);
		tvCancel.setVisibility(View.VISIBLE);
		lineDivide.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(dialogConfig.getCancelBtn())) {
			tvCancel.setText(dialogConfig.getCancelBtn());
		}
		tvCancel.setOnClickListener(getClickListener(dialogConfig.getCancelClickListener()));

		final LinearLayout llContent = dialog.findViewById(R.id.llContent);
		//根据内容多少行设置marginBottom
		tv_content.post(new Runnable() {
			@Override
			public void run() {
				int count = tv_content.getLineCount();
				LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llContent
						.getLayoutParams();
				if (count >= 2) {
					layoutParams.bottomMargin = UIUtil.dip2px(25);
				}
				llContent.setLayoutParams(layoutParams);
			}
		});

		TextView tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(dialogConfig.getTitle());
		dialog.setCancelable(dialogConfig.isCancelable());

		return dialog;
	}

	public View.OnClickListener getClickListener(View.OnClickListener onClickListener) {
		if (onClickListener == null) {
			onClickListener = defaultOnClickListener;
		}
		return onClickListener;
	}

	public View.OnClickListener defaultOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			DialogUtil.getInstance().dismissDialog();
		}
	};
}
