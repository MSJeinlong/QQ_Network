package com.qq.server.common;

/**
 * @Author:long
 * @Date: 2018/12/5 10:45
 * @Version 1.0
 */
public class User implements java.io.Serializable{
    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
