package com.example.hansangwon.mutoyou.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hansangwon.mutoyou.Data.UserData;

/**
 * Created by user on 2017-05-09.
 */

public class ContextUtil {

    public static UserData loginUserData = null;

    public static void setUserData(Context context, UserData userData) {
        SharedPreferences prefs = context.getSharedPreferences("MutoYouPref",
                Context.MODE_PRIVATE);

        prefs.edit().putInt("UserPKey", userData.PKey).commit();
        prefs.edit().putString("UserID", userData.userId).commit();
        prefs.edit().putString("UserName", userData.userName).commit();
        loginUserData = userData;
    }

    public static UserData getMyUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("MutoYouPref",
                Context.MODE_PRIVATE);
        if (loginUserData == null) {
            loginUserData = new UserData();
            loginUserData.PKey = prefs.getInt("UserPKey", -1);
            loginUserData.userId = prefs.getString("UserID", "");
            loginUserData.userName = prefs.getString("UserName", "");
        }
        return loginUserData;
    }

    public static void clearMyUserData(Context context){
        SharedPreferences prefs = context.getSharedPreferences("MutoYouPref",Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    };

}
