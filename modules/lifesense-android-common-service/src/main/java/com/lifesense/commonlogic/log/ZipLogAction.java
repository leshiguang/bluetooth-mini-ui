package com.lifesense.commonlogic.log;

/**
 * Created by yunfeng on 2017/2/8.
 */

public class ZipLogAction {

    private String mZipFilePath = null;
    private OnZipResultListener mListener = null;

    public ZipLogAction(OnZipResultListener listener, String filePath) {
        mListener = listener;
        mZipFilePath = filePath;
    }

    public String getZipFilePath() {
        return mZipFilePath;
    }

    public void setZipFilePath(String zipFilePath) {
        this.mZipFilePath = zipFilePath;
    }

    public OnZipResultListener getListener() {
        return mListener;
    }

    public void setListener(OnZipResultListener listener) {
        this.mListener = listener;
    }

    public static interface  OnZipResultListener {
        public void onResult(boolean success, String filePath);
    }

}
