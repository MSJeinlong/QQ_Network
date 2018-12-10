package com.qq.client.model;

import com.qq.client.common.User;

/**
 * @Author:long
 * @Date: 2018/12/5 10:55
 * @Version 1.0
 */
public class QQClientUser {

    public boolean checkUser(User u){
       return new QQClientConServer().SendInfoToServer(u);
    }
}
