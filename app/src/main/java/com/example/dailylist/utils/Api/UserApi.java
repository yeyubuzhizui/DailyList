package com.example.dailylist.utils.Api;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.UserBean;
import com.example.dailylist.utils.OkHttpUtils;

/**
 * @Author 夜雨
 * @ClassName User
 * @Description TODO
 * @Date 2022/12/2 15:35
 * @Version 1.0
 */

public class UserApi {
    public static JSONObject login(UserBean user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/users/login")
                .addParam("user_name", username)
                .addParam("password", password)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(false)
                .sync();
        Log.i("response", response);
        if (response.indexOf("请求失败") != -1) {
            return null;
        }
        return JSONObject.parseObject(response);
    }

    public static JSONObject register(UserBean user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/users/register")
                .addParam("user_name", username)
                .addParam("password", password)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(false)
                .sync();
        Log.i("heiye", response);
        return JSONObject.parseObject(response);
    }
}
