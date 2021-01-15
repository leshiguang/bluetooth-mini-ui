package com.lifesense.android.health.service.devicedetails.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.lifesense.android.health.service.R;
import com.lifesense.utils.UIUtil;


/**
 * Create by qwerty
 * Create on 2020/6/10
 **/
public class AlertDialogFragment extends DialogFragment {
    private AppCompatActivity activity;

    private TextView tvTitle;

    private TextView tvMsg;

    private TextView btPositive;

    private TextView btNegative;

    private View.OnClickListener positiveOnClickListener;

    private View.OnClickListener negativeOnClickListener;

    private static AlertDialogFragment newInstance(Builder builder) {
        Bundle args = new Bundle();
        AlertDialogFragment fragment = new AlertDialogFragment();
        args.putParcelable("builder_params", builder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(activity).inflate(R.layout.scale_fragment_common_dialog, container, false);
        tvTitle = contentView.findViewById(R.id.tv_title);
        tvMsg = contentView.findViewById(R.id.tv_message);
        btPositive = contentView.findViewById(R.id.bt_positive);
        btNegative = contentView.findViewById(R.id.bt_negative);
        Builder builder = getArguments().getParcelable("builder_params");
        setTitle(builder.title);
        setMsg(builder.msg);
        if (!TextUtils.isEmpty(builder.positiveText)) {
            setPositiveText(builder.positiveText);
        }

        if (!TextUtils.isEmpty(builder.negativeText)) {
            setNegativeText(builder.negativeText);
        }

        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(positiveOnClickListener != null) {
                    positiveOnClickListener.onClick(v);
                }
            }
        });

        btNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(negativeOnClickListener != null) {
                    negativeOnClickListener.onClick(v);
                }
            }
        });

        return contentView;
    }

    public void setPositiveOnClickListener(View.OnClickListener positiveOnClickListener) {
        this.positiveOnClickListener = positiveOnClickListener;
    }

    public void setNegativeOnClickListener(View.OnClickListener negativeOnClickListener) {
        this.negativeOnClickListener = negativeOnClickListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setMsg(String msg) {
        tvMsg.setText(msg);
    }

    public void setPositiveText(String text) {
        btPositive.setText(text);
    }

    public void setNegativeText(String text) {
        btNegative.setText(text);
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams params  = getDialog().getWindow().getAttributes();
        params.width = UIUtil.getScreenWidthPixels(getContext()) /4 * 3;
        getDialog().getWindow().setAttributes(params);
        getDialog().getWindow().setBackgroundDrawable(null);
    }

    public void show(FragmentManager manager) {
        show(manager,getClass().getSimpleName() + "-" + System.currentTimeMillis());
    }

    public static class Builder implements Parcelable {
        private String title;
        private String msg;
        private String positiveText;
        private String negativeText;

        private View.OnClickListener positiveOnClickListener;

        private View.OnClickListener negativeOnClickListener;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setPositiveClickListener(String text, View.OnClickListener onClickListener) {
            this.positiveText = text;
            this.positiveOnClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeClickListener(String text, View.OnClickListener onClickListener) {
            this.negativeText = text;
            this.negativeOnClickListener = onClickListener;
            return this;
        }

        public AlertDialogFragment build() {
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(this);
            alertDialogFragment.setNegativeOnClickListener(negativeOnClickListener);
            alertDialogFragment.setPositiveOnClickListener(positiveOnClickListener);
            return alertDialogFragment;
        }


        public Builder() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.msg);
            dest.writeString(this.positiveText);
            dest.writeString(this.negativeText);
        }

        protected Builder(Parcel in) {
            this.title = in.readString();
            this.msg = in.readString();
            this.positiveText = in.readString();
            this.negativeText = in.readString();
            this.positiveOnClickListener = in.readParcelable(View.OnClickListener.class.getClassLoader());
            this.negativeOnClickListener = in.readParcelable(View.OnClickListener.class.getClassLoader());
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel source) {
                return new Builder(source);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };
    }
}
