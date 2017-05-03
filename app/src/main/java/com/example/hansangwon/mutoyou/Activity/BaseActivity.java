package com.example.hansangwon.mutoyou.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2017-05-03.
 */

public class BaseActivity extends AppCompatActivity {

    public Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    public void bindViews(){

    }

    public void setValues(){

    }

    public void setupEvents(){

    }

    public void setCustomActionbar(){

    }
}
