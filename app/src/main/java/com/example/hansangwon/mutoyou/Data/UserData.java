package com.example.hansangwon.mutoyou.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by user on 2017-05-09.
 */

public class UserData implements Serializable {
    public int PKey = -1;
    public String userId = null;
    public String userName = null;

    public static UserData getUserDataFromJson(JSONObject jsonObject) {
        UserData data =  new UserData();

        try {
            data.PKey = jsonObject.getInt("pkey");
            data.userId = jsonObject.getString("id");
            data.userName = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

}
