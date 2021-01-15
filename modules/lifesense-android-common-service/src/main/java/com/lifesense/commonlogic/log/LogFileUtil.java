package com.lifesense.commonlogic.log;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by yunfeng on 2017/2/10.
 */

public class LogFileUtil {

    public static long getFileSize(String dirPath, String fileName) {
        if(TextUtils.isEmpty(dirPath) || TextUtils.isEmpty(fileName)) {
            return 0;
        }
        try {
            File dirFile = new File(dirPath);
            if(!dirFile.exists()) {
                return 0;
            }
            File pathFile = new File(dirFile,fileName);
            if(!pathFile.exists()) {
                return 0;
            }
            return pathFile.length();
        } catch(Exception e) {

        }
        return 0;
    }

    public static boolean changeFile(String dirFilePath, String srcFileName, String targetFileName) {
        if(TextUtils.isEmpty(dirFilePath) || TextUtils.isEmpty(srcFileName) || TextUtils.isEmpty(targetFileName)) {
            return false;
        }
        try {
            long time1 = System.currentTimeMillis();
            File dirFile = new File(dirFilePath);
            if(!dirFile.exists()) {
                return false;
            }
            File pathFile = new File(dirFilePath,targetFileName);
            if(pathFile.exists()) {
                pathFile.delete();
            }
            File wringFile = new File(dirFilePath,srcFileName);
            if(wringFile.exists()) {
                wringFile.renameTo(pathFile);
            }
            android.util.Log.d("testLog","file half time: " + (System.currentTimeMillis() - time1));
            return true;
        } catch(Exception e) {

        } finally {
        }
        return false;
    }

    /**
     * 压缩文件,文件夹
     *
     * @param srcFilePath 要压缩的文件/文件夹名字
     * @param zipFilePath 指定压缩的目的和名字
     * @throws Exception
     */
    public static void zipFolder(String srcFilePath, String zipFilePath) {
        long time1 = System.currentTimeMillis();
        ZipOutputStream outZip = null;
        try {
            outZip = new ZipOutputStream(new FileOutputStream(zipFilePath));
            File file = new File(srcFilePath);
            zipFiles(file.getParent() + File.separator, file.getName(), outZip);

            outZip.finish();

        } catch(Exception e) {

        } finally {
            if(outZip != null) {
                try {
                    outZip.close();
                } catch(Exception e) {

                }
            }
        }
        android.util.Log.d("testLog","zip file time: " + (System.currentTimeMillis() - time1));
    }

    /**
     * 压缩文件
     *
     * @param folderPath
     * @param filePath
     * @param zipOut
     * @throws Exception
     */
    private static void zipFiles(String folderPath, String filePath, ZipOutputStream zipOut) throws Exception {
        if (zipOut == null) {
            return;
        }
        File file = new File(folderPath + filePath);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            zipOut.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOut.write(buffer, 0, len);
            }
            zipOut.closeEntry();
        } else {
            //文件夹的方式,获取文件夹下的子文件
            String fileList[] = file.list();

            //如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(filePath + File.separator);
                zipOut.putNextEntry(zipEntry);
                zipOut.closeEntry();
            }

            //如果有子文件, 遍历子文件
            for (String aFileList : fileList) {
                zipFiles(folderPath, filePath + File.separator + aFileList, zipOut);
            }
        }
    }


}
