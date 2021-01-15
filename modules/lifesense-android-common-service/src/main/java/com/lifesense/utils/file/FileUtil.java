package com.lifesense.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.lifesense.utils.LSLog;
import com.lifesense.utils.StringUtil;

public class FileUtil {
    public static String getNetworkLogPath(String appFilePath) {
        StringBuilder sb = new StringBuilder();
        sb.append(appFilePath);
        sb.append("/networkLog/");
        File file = new File(sb.toString());
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        return sb.toString();
    }

    private static final String PATH_SEPERATOR = "/";
    private static final String TAG = "FileUtil";

    /**
     * 用来合并文件夹用的格式化字符串
     * 格式:a/b
     */
    protected static final String FOLDER_MERGE_FORMATER = "%s/%s";

    /**
     * 格式:a/b/
     */
    protected static final String FOLDER_MERGE_FORMATERLAST = "%s/%s/";

    public static boolean ensureDir(String path) {
        if (null == path) {
            return false;
        }

        boolean ret = false;

        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            try {
                ret = file.mkdirs();
                LSLog.d(TAG, String.format("create dir(%s)", path));
            } catch (SecurityException se) {
                LSLog.e(TAG, String.format("create dir(%s) fail", path));
            }
        }

        return ret;
    }


    /**
     * 文件是否存在
     * 如果文件不存在,或者文件是目录,则返回false
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        if (null == path) {
            return false;
        }

        boolean ret = false;

        File file = new File(path);
        ret = file.exists() && !file.isDirectory();

        return ret;
    }

    /**
     * 获得文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        String result = null;
        if (StringUtil.isEmptyOrNull(filePath)) {
            return result;
        }

        int lastSeperatorIndex = filePath.lastIndexOf(PATH_SEPERATOR);
        if (lastSeperatorIndex > 0) {
            result = filePath.substring(++lastSeperatorIndex);
        }

        return result;
    }


    /**
     * 获得文件路径
     *
     * @param filePath
     * @return
     */
    public static String getFileDir(String filePath) {
        String result = null;
        if (StringUtil.isEmptyOrNull(filePath)) {
            return result;
        }

        int lastSeperatorIndex = filePath.lastIndexOf(PATH_SEPERATOR);
        if (lastSeperatorIndex > 0) {
            result = filePath.substring(0, lastSeperatorIndex);
        }

        return result;
    }

    /**
     * 删除目录（空目录或者非空目录）
     *
     * @param dir
     */
    public static void deleteDir(File dir) {
        // 检查参数
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            LSLog.i(TAG, String.format("cancel to delete dir:%s", dir.getPath()));
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除目录
            } else if (file.isDirectory()) {
                deleteDir(file); // 递规的方式删除目录
            }
        }
        //dir.delete(); // 删除目录本身
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        deleteFile(file);
    }

    /**
     * 从文件路径读取文件到byte[]
     *
     * @param path
     * @return
     */
    public static byte[] readByteFromPath(String path) {
        File file = new File(path);

        if (path == null || !file.exists() || file.isDirectory()) {
            LSLog.w(TAG, String.format("readByteFromPath the file path empty/is directory/not exist:%s", path));
        } else {
            if (!file.canRead()) {
                LSLog.w(TAG, String.format("readByteFromPath the file can not read:%s", path));
            } else {
                try {
                    FileInputStream is = new FileInputStream(file);
                    int length = is.available();

                    byte[] bytes = new byte[length];
                    is.read(bytes);
                    is.close();
                    is = null;

                    return bytes;
                } catch (FileNotFoundException e) {
                    LSLog.e(TAG, "FileNotFoundException", e);
                } catch (IOException e) {
                    LSLog.e(TAG, "IOException", e);
                }
            }
        }

        return null;
    }

    /**
     * 从文件路径读取文件到String
     *
     * @param path
     * @return
     */
    public static String readStringFromPath(String path) {
        String result = null;
        byte[] bytes = readByteFromPath(path);
        if (bytes != null) {
            result = new String(bytes);
        }
        return result;
    }

    /**
     * 从assets目录之后算起,如:configfilecenter/common.json
     *
     * @param assetsPath
     * @return
     */
    public static String readStringFromAssetsPath(Context context, String assetsPath) {
        String result = null;
        if (context == null || assetsPath == null) {
            return result;
        }
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(assetsPath);
            int length = inputStream.available();

            byte[] bytes = new byte[length];
            inputStream.read(bytes);
            inputStream.close();
            inputStream = null;
            result = new String(bytes);
        } catch (IOException e) {
            LSLog.e(TAG, "readStringFromAssetsPath IOException", e);
        }

        return result;
    }

    /**
     * 写字符串到文件
     *
     * @param path       写文件路径
     * @param atomically 是否先写临时文件，成功后再重命名过去
     * @return
     */
    public static boolean writeStringToPath(String content, String path, boolean atomically) {
        boolean result = false;

        if (StringUtil.isEmptyOrNull(path)) {
            LSLog.w(TAG, String.format("writeStringToPath can not write to empty path:%s", path));
            return result;
        }

        if (content == null) {
            LSLog.w(TAG, String.format("writeStringToPath content is null content(%s) to path:%s", content, path));
            return result;
        }

        ensureDir(FileUtil.getFileDir(path));
        File file = new File(path);

        if (file.isDirectory()) {
            LSLog.w(TAG, String.format("writeStringToPath can not write to a directory:%s", path));
            return result;
        }

        //atomically逻辑
        File writeFile = null;
        File targetFile = null;
        if (atomically) {
            writeFile = new File(String.format("%s.tmp", path));
            targetFile = file;
        } else {
            writeFile = file;
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(writeFile);
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            result = true;
            LSLog.d(TAG, String.format("writeStringToPath successfully, %s, %s", StringUtil.subCenterString(content, 200, 30), path));
        } catch (FileNotFoundException e) {
            LSLog.e(TAG, String.format("writeStringToPath FileNotFoundException:%s", path), e);
        } catch (IOException e) {
            LSLog.e(TAG, String.format("writeStringToPath IOException:%s", path), e);
        }

        //重命名回去
        if (atomically) {
            writeFile.renameTo(targetFile);
        }

        return result;
    }

    /**
     * 获得SD卡目录
     *
     * @return
     */
    public static String getSDCardDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 是否sdk卡存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        try {
            return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState());
        } catch (Throwable e) {
            //异常保护，部分rom上面方法有空指针崩溃
            //https://bugly.qq.com/v2/crash-reporting/crashes/e711e9fe1c/7388?pid=1
            return false;
        }
    }

    /**
     * App Data目录
     *
     * @return
     */
    public static String getAppDataDir(Context context) {
        return context.getApplicationInfo().dataDir;
    }

    /**
     * App Data files目录
     *
     * @return
     */
    public static String getAppDataFilesDir(Context context) {
        return String.format("%s/%s", getAppDataDir(context), "files");
    }

    public static void main(String[] args) {
        writeStringToPath("abc", "~/Temp/filetemp.txt", true);
    }


    public static boolean renameTo(String oldPath, String newPath) {
        if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath)) {
            return false;
        }

        File file = new File(oldPath);
        if (!file.exists()) {
            return false;
        }

        return file.renameTo(new File(newPath));
    }

    public static boolean deletedFile(String path) {
        boolean flag = false;
        File file = new File(path);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    public static void deletedFile(String[] paths) {
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                deletedFile(path);
            }
        }
    }


    public static boolean deleteFile(File file) {
        if (file == null) {
            return false;
        }
        boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    public static void deleteFile(File[] files) {
        if (files == null || files.length == 0) {
            return ;
        }
        for (File file : files) {
            deleteFile(file);
        }
    }
}
