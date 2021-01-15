package com.lifesense.android.health.service.util.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

/**
 * @author Sinyi.liu
 * @date 2017/9/26 10:40
 * @describe:
 */
public class DialogConfig {
    private Context mContext;
    private String mTitle;
    private CharSequence mContent;
    private String mCancelBtn; //left
    private String mConfirmBtn;//right
    private View.OnClickListener mCancelClickListener;
    private View.OnClickListener mConfirmClickListener;
	private boolean isCancelable=true;
	private int gravity;

    public boolean isCancelable() {
        return isCancelable;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    private DialogConfig(Builder builder) {
		mContext = builder.mContext;
		mTitle = builder.mTitle;
		mContent = builder.mContent;
        mCancelBtn = builder.mCancelBtn;
        mConfirmBtn = builder.mConfirmBtn;
        mCancelClickListener = builder.mCancelClickListener;
        mConfirmClickListener = builder.mConfirmClickListener;
        isCancelable=builder.isCancelable;
        gravity=builder.gravity;
	}

    public Context getContext() {
        return mContext;
    }

    public String getTitle() {
        return mTitle;
    }

    public CharSequence getContent() {
        return mContent;
    }

    public DialogConfig setContext(Context context) {
        mContext = context;
        return this;
    }

    public DialogConfig setTitle(String title) {
        mTitle = title;
        return this;
    }

    public DialogConfig setContent(String content) {
        mContent = content;
        return this;
    }

    public String getCancelBtn() {
        return mCancelBtn;
    }

    public DialogConfig setCancelBtn(String cancelBtn) {
        mCancelBtn = cancelBtn;
        return this;
    }

    public String getConfirmBtn() {
        return mConfirmBtn;
    }

    public DialogConfig setConfirmBtn(String confirmBtn) {
        mConfirmBtn = confirmBtn;
        return this;
    }

    public View.OnClickListener getCancelClickListener() {
        return mCancelClickListener;
    }

    public DialogConfig setCancelClickListener(View.OnClickListener cancelClickListener) {
        mCancelClickListener = cancelClickListener;
        return this;
    }

    public View.OnClickListener getConfirmClickListener() {
        return mConfirmClickListener;
    }

    public DialogConfig setConfirmClickListener(View.OnClickListener confirmClickListener) {
        mConfirmClickListener = confirmClickListener;
        return this;
    }

    public static class Builder {
        public Builder(Context context){
            setContext(context);
        }
		private Context mContext;
		private String mTitle;
        private CharSequence mContent;
        private String mCancelBtn; //left
		private String mConfirmBtn;//right
		private View.OnClickListener mCancelClickListener;
		private View.OnClickListener mConfirmClickListener;
        private boolean isCancelable=true;
        private int gravity= Gravity.CENTER;

        public int getGravity() {
            return gravity;
        }

        public void setGravity(int gravity) {
            this.gravity = gravity;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public DialogConfig build() {
			return new DialogConfig(this);
		}
		public Builder setContext(Context context) {
			mContext = context;
			return this;
		}

		public Builder setTitle(String title) {
			mTitle = title;
			return this;
		}

        public Builder setContent(CharSequence content) {
            mContent = content;
			return this;
		}

        public Builder setCancelBtn(String cancelBtn) {
            mCancelBtn = cancelBtn;
            return this;
        }

        public Builder setConfirmBtn(String confirmBtn) {
            mConfirmBtn = confirmBtn;
            return this;
        }

        public Builder setCancelClickListener(View.OnClickListener cancelClickListener) {
            mCancelClickListener = cancelClickListener;
            return this;
        }

        public Builder setConfirmClickListener(View.OnClickListener confirmClickListener) {
            mConfirmClickListener = confirmClickListener;
            return this;
        }
    }
}
