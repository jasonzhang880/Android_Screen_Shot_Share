package com.lab.jason.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class TestAty extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test);

        imageView=(ImageView)findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                screenShotShare(getWindow().getDecorView());//传入当前Activity的顶层视图
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void textshare(View view) {
        // 最后一个参数可以传递一个图片路径，如果图片存在将会发送带图片的分享
        Share.startShare(this, "分享", "sloop的分享", "这是文字分享内容部分", null);
    }

    public void screenShotShare(View view) {
        Share.screenShare(this,imageView,"分享", "截屏分享", "分享的文字内容");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppPath.deleteScreenShotFile(this);//清空保存截屏文件的缓存目录
    }
}
