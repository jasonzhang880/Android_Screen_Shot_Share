package com.lab.jason.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class ShotScreen {
    private static final String TAG = "shotscreen";

    /**
     * 获取屏幕的bitmap
     * @param v		需要截屏的view
     * @return				返回一个bitmap
     */
    private static Bitmap takeScreenShot(View v) {
        v.clearFocus(); // 清除视图焦点
        v.setPressed(false);// 将视图设为不可点击

        boolean willNotCache = v.willNotCacheDrawing(); // 返回视图是否可以保存他的画图缓存
        v.setWillNotCacheDrawing(false);

        //将视图在此操作时置为透明
        int color = v.getDrawingCacheBackgroundColor(); // 获得绘制缓存位图的背景颜色
        v.setDrawingCacheBackgroundColor(0); // 设置绘图背景颜色
        if (color != 0) { // 如果获得的背景不是黑色的则释放以前的绘图缓存
            v.destroyDrawingCache(); // 释放绘图资源所使用的缓存
        }
        v.buildDrawingCache(); // 重新创建绘图缓存，此时的背景色是黑色
        Bitmap cacheBitmap = v.getDrawingCache(); // 将绘图缓存得到的,注意这里得到的只是一个图像的引用
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap); // 将位图实例化
        //恢复视图
        v.destroyDrawingCache();// 释放位图内存
        v.setWillNotCacheDrawing(willNotCache);// 返回以前缓存设置
        v.setDrawingCacheBackgroundColor(color);// 返回以前的缓存颜色设置

        return bitmap;
    }


    /**
     * 保存截图
     * @param bitmap		需要保存的bitmap
     * @param savePath		保存的路径
     * @param filename		保存的名称
     */
    private static void savePic(Bitmap bitmap, String savePath, String filename) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(savePath+filename);
            if (fileOutputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Exception:FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException:IOException");
            e.printStackTrace();
        }
    }

    /**
     * 截屏
     * @param activity		需要截屏的activity名称
     * @param savePath		截图文件保存的路径
     * @param filename		截图保存的名称
     */
    @SuppressWarnings("unused")
    public static void shot(Activity activity,View view, String savePath, String filename) {
        ShotScreen.savePic(ShotScreen.takeScreenShot(view), savePath, filename);
        System.out.println("截屏成功");
    }

    /**
     * 截屏
     * @param activity		需要截屏的activity名称
     * @return				截屏保存的文件路径
     */
    public static String shot(Activity activity,View view) {
        String imagePath = AppPath.getScreenShotPath(activity);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        String time=formatter.format(curDate);
        String imageName = "shot_"+time+".png";

        ShotScreen.shot(activity,view,imagePath, imageName);
        Log.d("screen","ShotScreen.shot()"+imagePath+imageName);
        return imagePath+imageName;

    }
}
