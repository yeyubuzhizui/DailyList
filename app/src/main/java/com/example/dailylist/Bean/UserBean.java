package com.example.dailylist.Bean;

import java.time.LocalDateTime;

/**
 * @Author: 夜雨
 * @ClassName: UserBean
 * @Description: TODO
 * @Date: 2022/12/7 2:17
 * @Version: 1.0
 */

public class UserBean {
    private int uid;
    private String username;
    private String password;
    private String email;
    private String userAvatarUrl;
    private LocalDateTime registerTime;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }


}
