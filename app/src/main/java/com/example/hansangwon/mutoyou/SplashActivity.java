package com.example.hansangwon.mutoyou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.hansangwon.mutoyou.Activity.BaseActivity;
import com.example.hansangwon.mutoyou.Util.ContextUtil;

/**
 * Created by yrs00 on 2016-12-18.
 */

public class SplashActivity extends BaseActivity {
    private android.widget.ImageView splashImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bindViews();
        setValues();
        setupEvents();

    }


    @Override
    public void setValues() {
        super.setValues();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap splash = BitmapFactory.decodeResource(getResources(),R.drawable.main, options);
        splashImage.setImageBitmap(splash);

    }


    @Override
    public void setupEvents() {
        super.setupEvents();

        final Intent myint = new Intent(this,LoginActivity.class);
        Handler handler = new Handler(){
            public void handleMessage(Message msg){
//                startActivity(myint);
//                finish();
                moveToProperActivity();
            }
        };

        handler.sendEmptyMessageDelayed(0,3000);


    }

    void moveToProperActivity() {

        if (ContextUtil.getMyUserData(mContext).PKey == -1) {

            // TODO - 로그인 화면으로 이동해야함


            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        }
        else {

            // 메인화면으로 이동

            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        }

        finish();

    }

    @Override
    public void bindViews() {
        super.bindViews();
        this.splashImage = (ImageView) findViewById(R.id.splashImage);

    }
}
