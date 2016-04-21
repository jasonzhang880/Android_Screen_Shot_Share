package com.lab.jason.test;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class AppPath {

    /**
     * 获取当前应用对应的文件夹(私有目录/data/data/包名/)
     * @return
     */
    public static String getAppPath(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/";
    }

    /**
     * 获取当前应用对应的文件夹(优先选择外置路径)
     *
     * @return
     */
    public static String getAppFilePath(Context context) {
        String PATH = null;
        if (isExternalExist()) {
            PATH = Environment.getExternalStorageDirectory() + "/sloop/Demo/";//注意这里是存储卡文件夹路径
            File Dir = new File(PATH);
            if (!(Dir.exists() && Dir.isDirectory())) {
                Dir.mkdirs();
            }

        } else {
            PATH = context.getFilesDir().getAbsolutePath() + "/";
        }
        Log.d("screen","AppPath.appFilePath"+PATH);
        return PATH;
    }
    /**
     * 获取截屏的文件夹(优先选择外置路径)
     *
     * @return
     */
    public static String getScreenShotPath(Context context) {
        String PATH = getAppFilePath(context)+"screenshot/";
        File Dir = new File(PATH);
        if (!(Dir.exists() && Dir.isDirectory())) {
            Dir.mkdirs();
        }
        Log.d("screen","AppPath.screenShotPath"+PATH);
        return PATH;
    }

    /**
     * 判断位置存储卡是否存在
     *
     * @return
     */
    public static boolean isExternalExist() {

        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 删除截屏文件夹
     * path
     * 文件夹路径
     */
    public static void deleteScreenShotFile(Activity activity) {
        String path= AppPath.getScreenShotPath(activity);
        File dir = new File(path);
        delete(dir);
    }

    /**
     * 删除文件（夹）
     * @param file
     * 使用递归删除文件夹
     */
    private static void delete(File file) {

        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles==null || childFiles.length==0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }

            file.delete();
        }
    }
}
