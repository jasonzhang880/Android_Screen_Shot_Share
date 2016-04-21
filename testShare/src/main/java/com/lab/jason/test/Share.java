package com.lab.jason.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class Share {

    public static void startShare(Context context, String shareTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || "".equals(imgPath)) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File image = new File(imgPath);
            if (image != null && image.exists() && image.isFile()) { //image不为空，且对应的file对象存在，且对应的是文件非目录
                intent.setType("image/png");
                Log.d("screen","Share.startShare.imgPath"+imgPath);
                Uri u = Uri.fromFile(image);
                Log.d("screen","Share.startShare.uri"+u.toString());
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }else {
                intent.setType("text/plain"); // 纯文本
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, shareTitle));

    }

    /**
     * 截屏分享
     * @param activity		需要截屏的activity
     * @param view         需要截屏的具体view
     * @param shareTitle	分享窗口名称
     * @param msgTitle		分享内容的标题
     * @param msgText		分享的信息
     */
    public static void screenShare(Activity activity, View view ,String shareTitle, String msgTitle, String msgText) {
        String path = ShotScreen.shot(activity,view);
        startShare(activity, shareTitle, msgTitle, msgText, path);
    }
}
