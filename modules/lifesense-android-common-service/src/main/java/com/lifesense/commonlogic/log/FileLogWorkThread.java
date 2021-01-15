package com.lifesense.commonlogic.log;

import android.text.TextUtils;


import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yunfeng on 2017/2/8.
 */

public class FileLogWorkThread {

    private ArrayList<FileLogBean> mLogBeanList = new ArrayList<>();
    private volatile ZipLogAction mZipLogAction = null;
    private boolean mIsLogging = true;
    private final Object mThreadLockObject = new Object();
    private LogInitInfo mLogInitInfo = null;
    private volatile WorkThread mWorkThread = null;

    public FileLogWorkThread(LogInitInfo initInfo) {
        mLogInitInfo = initInfo;
        startFilelog();
    }

    public void addLogBean(FileLogBean logBean) {
        if (logBean == null) {
            return;
        }
        synchronized (mLogBeanList) {
            mLogBeanList.add(logBean);
        }
        synchronized (mThreadLockObject) {
            mThreadLockObject.notify();
        }
    }

    public void setZipFileAction(ZipLogAction action) {
        if (mZipLogAction == null && action != null) {
            mZipLogAction = action;
            synchronized (mThreadLockObject) {
                mThreadLockObject.notify();
            }
        }
    }

    public void stopLog() {
        mIsLogging = false;
    }

    private void startFilelog() {
        if (mWorkThread == null) {
            mWorkThread = new WorkThread();
            mWorkThread.start();
        }
    }

    private class WorkThread extends Thread {

        private String mFileDir = null;
        private String mFileName = null;
        private String mFileWritingName = null;
        private FileWriter mFileWriter = null;
        private int mCheckFileSizeCount = 1;

        public WorkThread() {
            if (mLogInitInfo != null) {
                mFileDir = mLogInitInfo.getFileDir();
                mFileName = mLogInitInfo.getFileName();
            }
            if(!TextUtils.isEmpty(mFileName)) {
                mFileWritingName = mFileName + "ing";
            }
        }

        @Override
        public void run() {
            try {
                if (mFileDir == null || mFileWritingName == null) {
                    return;
                }

                while (mIsLogging) {
                    if (mZipLogAction != null) {
                        //压缩文件
                        closeFos();
                        zipLogFile();
                        mZipLogAction = null;
                    }

                    FileLogBean logBean = null;
                    synchronized (mLogBeanList) {
                        if (mLogBeanList.size() > 0) {
                            logBean = mLogBeanList.remove(0);
                        }
                    }
                    if (logBean == null && mZipLogAction == null) {
                        closeFos();
                        synchronized (mThreadLockObject) {
                            mThreadLockObject.wait();
                        }
                    }

                    if (logBean != null) {
                        checkLogFileSize();
                        openFos();
                        writeLogBean(logBean);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                synchronized (mLogBeanList) {
                    mLogBeanList.clear();
                }
                mZipLogAction = null;
                mWorkThread = null;
            }
        }

        private void writeLogBean(FileLogBean logBean) {
            if (logBean == null || mFileWriter == null) {
                return;
            }
            try {
                StringBuffer sb = new StringBuffer();
                Date date = new Date(logBean.getTime());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                String dateStr = format.format(date);
                sb.append(dateStr);
                sb.append("  ");
                sb.append(logBean.getLogLevelStr());
                sb.append("  ");
                sb.append(logBean.getTag());
                sb.append("  ");
                sb.append(logBean.getContent());
                sb.append("\n");
                mFileWriter.write(sb.toString());
            } catch (Exception e) {
            }
        }

        private boolean checkLogFileSize() {
            if(mCheckFileSizeCount % LogConstant.CHECK_FILE_SIZE_COUNT == 0) {
                mCheckFileSizeCount = 0;
                long fileSize = LogFileUtil.getFileSize(mFileDir,mFileWritingName);
                if(fileSize >= LogConstant.MAX_LOG_FILE_SIZE) {
                    closeFos();
                    return LogFileUtil.changeFile(mFileDir,mFileWritingName,mFileName);
                }
            }
            mCheckFileSizeCount++;
            return false;
        }

        private void openFos() {
            if (mFileDir == null || mFileWritingName == null) {
                return;
            }
            if (mFileWriter == null) {
                try {
                    File dirFile = new File(mFileDir);
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    File pathFile = new File(dirFile, mFileWritingName);
                    if (!pathFile.exists()) {
                        pathFile.createNewFile();
                    }
                    mFileWriter = new FileWriter(pathFile, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void closeFos() {
            if (mFileWriter != null) {
                try {
                    mFileWriter.close();
                } catch (Exception e) {
                }
                mFileWriter = null;
            }
        }

        private void zipLogFile() {
            if (mZipLogAction == null) {
                return;
            }
            ZipLogAction.OnZipResultListener listener = mZipLogAction.getListener();
            try {
                File zipFile = new File(mZipLogAction.getZipFilePath());
                zipFile.deleteOnExit();
                LogFileUtil.zipFolder(mFileDir,mZipLogAction.getZipFilePath());
                if(listener != null) {
                    listener.onResult(true,mZipLogAction.getZipFilePath());
                    return;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            if(listener != null) {
                listener.onResult(false,mZipLogAction.getZipFilePath());
            }
        }

    }


}
